// File: OnboardingFragment1.kt
package com.raditya.smartwaterguard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.raditya.smartwaterguard.databinding.FragmentOnboarding1Binding

class OnboardingFragment1 : Fragment() {

    // Gunakan underscore (_) untuk properti yang bisa null
    private var _binding: FragmentOnboarding1Binding? = null

    // Properti non-null yang dapat diakses oleh Activity/Fragment lain
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate layout menggunakan View Binding
        _binding = FragmentOnboarding1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Atur teks menggunakan String Resources yang sudah diperbarui
        binding.onboardingTitle.text = getString(R.string.onboarding_title_1)
        binding.onboardingDescription.text = getString(R.string.onboarding_desc_1)

        // Logika atau inisialisasi tambahan untuk Slide 1
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Penting: Hapus referensi binding saat View dihancurkan untuk menghindari memory leak
        _binding = null
    }
}