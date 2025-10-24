// File: OnboardingPagerAdapter.kt
package com.raditya.smartwaterguard

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// Ubah konstruktor untuk menerima DAFTAR FRAGMENT
class OnboardingPagerAdapter(
    activity: FragmentActivity,
    private val fragments: List<Fragment> // <--- ARGUMEN KEDUA DITAMBAHKAN
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return fragments.size // Menggunakan ukuran daftar
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position] // Mengembalikan Fragment dari daftar
    }
}