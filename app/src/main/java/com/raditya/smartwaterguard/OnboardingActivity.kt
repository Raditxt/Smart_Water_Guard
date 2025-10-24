package com.raditya.smartwaterguard

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ArgbEvaluator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.raditya.smartwaterguard.databinding.ActivityOnboardingBinding
import androidx.viewpager2.widget.ViewPager2
import androidx.core.view.WindowCompat
import android.view.View
import android.view.animation.LinearInterpolator
import android.graphics.Color

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private val PREFS_NAME = "user_session"

    // =============================================
    // ANIMASI & AUTO SCROLL
    // =============================================
    private val FILL_DURATION: Long = 3000
    private var autoScrollActive = true
    private var currentAnimator: ObjectAnimator? = null
    private var firstAnimationStarted = false
    private lateinit var indicatorViews: List<View>

    // =============================================
    // FRAGMENT & WARNA
    // =============================================
    private val fragmentList = listOf<Fragment>(
        OnboardingFragment1(),
        OnboardingFragment2(),
        OnboardingFragment3()
    )

    private val pageStatusBarColors = listOf(
        R.color.primary_blue,
        R.color.deep_navy,
        R.color.primary_dark_blue
    )
    private val pageBackgroundColors = listOf(
        R.color.aqua_background,
        R.color.soft_gray_ai,
        R.color.sky_gradient_start
    )

    // =============================================
    // ON CREATE
    // =============================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        indicatorViews = listOf(
            binding.indicator1Foreground,
            binding.indicator2Foreground,
            binding.indicator3Foreground
        )

        require(indicatorViews.size == fragmentList.size) {
            "Jumlah indikator harus sama dengan jumlah fragment"
        }

        val pagerAdapter = OnboardingPagerAdapter(this, fragmentList)
        binding.viewPager.adapter = pagerAdapter

        setupNavigationAndColor()
    }

    override fun onPause() {
        super.onPause()
        currentAnimator?.cancel()
    }

    override fun onStop() {
        super.onStop()
        currentAnimator?.cancel()
    }

    // =============================================
    // ANIMASI SEGMENT
    // =============================================
    private fun startSegmentAnimation(position: Int) {
        currentAnimator?.cancel()
        if (!autoScrollActive || position >= indicatorViews.size) return

        val targetView = indicatorViews[position]
        targetView.scaleX = 0f
        targetView.pivotX = 0f

        currentAnimator = ObjectAnimator.ofFloat(targetView, "scaleX", 0f, 1f).apply {
            duration = FILL_DURATION
            interpolator = LinearInterpolator()

            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    animation.removeAllListeners()

                    if (!autoScrollActive) return

                    val nextItem = binding.viewPager.currentItem + 1
                    val lastIndex = fragmentList.size - 1

                    targetView.scaleX = 1f

                    // ✅ Reset flag supaya animasi halaman berikutnya bisa jalan
                    firstAnimationStarted = false

                    if (nextItem <= lastIndex) {
                        binding.viewPager.currentItem = nextItem
                    } else {
                        saveFirstLaunchStatus()
                        startActivity(Intent(this@OnboardingActivity, LoginActivity::class.java))
                        finish()
                    }
                }

            })
            start()
        }
    }

    private fun updateSegmentStatus(currentPosition: Int) {
        indicatorViews.forEachIndexed { index, view ->
            view.scaleX = if (index < currentPosition) 1f else 0f
        }
    }

    // =============================================
    // NAVIGASI, WARNA, DAN LISTENER
    // =============================================
    private fun setupNavigationAndColor() {
        val lastIndex = fragmentList.size - 1
        val argbEvaluator = ArgbEvaluator()

        setSystemBarsAndBackground(0)
        updateSegmentStatus(0)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (position < pageBackgroundColors.size - 1) {
                    val bgStart = ContextCompat.getColor(this@OnboardingActivity, pageBackgroundColors[position])
                    val bgEnd = ContextCompat.getColor(this@OnboardingActivity, pageBackgroundColors[position + 1])
                    val blendedBg = argbEvaluator.evaluate(positionOffset, bgStart, bgEnd) as Int
                    binding.main.setBackgroundColor(blendedBg)

                    val sbStart = ContextCompat.getColor(this@OnboardingActivity, pageStatusBarColors[position])
                    val sbEnd = ContextCompat.getColor(this@OnboardingActivity, pageStatusBarColors[position + 1])
                    val blendedSb = argbEvaluator.evaluate(positionOffset, sbStart, sbEnd) as Int
                    window.statusBarColor = blendedSb
                    window.navigationBarColor = blendedSb
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateSegmentStatus(position)

                if (autoScrollActive) {
                    if (!firstAnimationStarted || currentAnimator == null) {
                        firstAnimationStarted = true
                        startSegmentAnimation(position)
                    }
                }

                binding.btnNext.text = if (position == lastIndex) {
                    getString(R.string.button_mulai_sekarang)
                } else {
                    getString(R.string.button_lanjut)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                when (state) {
                    ViewPager2.SCROLL_STATE_DRAGGING -> {
                        currentAnimator?.cancel()
                        autoScrollActive = false
                        firstAnimationStarted = false

                        if (binding.viewPager.currentItem < indicatorViews.size) {
                            indicatorViews[binding.viewPager.currentItem].scaleX = 1f
                        }
                    }
                    ViewPager2.SCROLL_STATE_IDLE -> {
                        val currentItem = binding.viewPager.currentItem
                        setSystemBarsAndBackground(currentItem)
                    }

                    ViewPager2.SCROLL_STATE_SETTLING -> {
                        TODO()
                    }
                }
            }
        })

        binding.btnNext.setOnClickListener {
            currentAnimator?.cancel()
            autoScrollActive = false

            val currentItem = binding.viewPager.currentItem
            if (currentItem < lastIndex) {
                if (currentItem < indicatorViews.size) {
                    indicatorViews[currentItem].scaleX = 1f
                }
                binding.viewPager.currentItem = currentItem + 1
            } else {
                saveFirstLaunchStatus()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        // ⚠️ FIX: Jangan panggil startSegmentAnimation(0) di sini lagi!
    }

    private fun setSystemBarsAndBackground(position: Int) {
        val statusBarColor = ContextCompat.getColor(this, pageStatusBarColors[position])
        val backgroundColor = ContextCompat.getColor(this, pageBackgroundColors[position])

        binding.main.setBackgroundColor(backgroundColor)
        window.statusBarColor = statusBarColor
        window.navigationBarColor = statusBarColor

        val controller = WindowCompat.getInsetsController(window, window.decorView)
        val isLight = Color.luminance(statusBarColor) > 0.5
        controller.isAppearanceLightStatusBars = !isLight
    }

    private fun saveFirstLaunchStatus() {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        prefs.edit { putBoolean("FIRST_LAUNCH", false) }
    }
}
