// File: DashboardActivity.kt
package com.raditya.smartwaterguard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.raditya.smartwaterguard.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Muat Fragment default (Monitoring) saat pertama kali
        // savedInstanceState == null memastikan ini hanya dijalankan saat pertama kali Activity dibuat
        if (savedInstanceState == null) {
            loadFragment(MonitoringFragment())
        }

        // 2. Set Listener untuk Bottom Navigation
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_monitoring -> loadFragment(MonitoringFragment())
                R.id.nav_ai_analysis -> loadFragment(AIFragment())
                R.id.nav_recommendations -> loadFragment(RecommendationFragment())
                R.id.nav_profile -> loadFragment(ProfileFragment()) // Ganti dari nav_info
                else -> false
            }
            true // Mengindikasikan bahwa event klik sudah ditangani
        }
    }

    /**
     * Fungsi untuk mengganti Fragment di FragmentContainerView
     */
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}