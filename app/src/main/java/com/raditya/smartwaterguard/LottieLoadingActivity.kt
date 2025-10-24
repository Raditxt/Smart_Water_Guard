// File: LottieLoadingActivity.kt
package com.raditya.smartwaterguard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity // HARUS DARI AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.raditya.smartwaterguard.databinding.ActivityLottieLoadingBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LottieLoadingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLottieLoadingBinding
    private val PREFS_NAME = "user_session"
    private val MIN_LOADING_TIME = 2000L // Minimal 2 detik

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi View Binding
        binding = ActivityLottieLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root) // Memerlukan tema AppCompat

        // Logika loading
        lifecycleScope.launch {
            // Tahan selama minimal 2 detik
            delay(MIN_LOADING_TIME)

            // Ambil data sesi (Logika navigasi 3-tingkat)
            val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            val isLoggedIn = prefs.getBoolean("IS_LOGGED_IN", false)
            val isFirstLaunch = prefs.getBoolean("FIRST_LAUNCH", true)

            // Tentukan Activity berikutnya: Dashboard -> Onboarding -> Login
            val nextActivityClass = when {
                isLoggedIn -> DashboardActivity::class.java
                isFirstLaunch -> OnboardingActivity::class.java
                else -> LoginActivity::class.java
            }

            // Navigasi
            startActivity(Intent(this@LottieLoadingActivity, nextActivityClass))
            finish()
        }
    }
}