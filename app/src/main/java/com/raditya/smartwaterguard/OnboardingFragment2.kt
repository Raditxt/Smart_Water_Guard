// File: OnboardingFragment2.kt
package com.raditya.smartwaterguard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.raditya.smartwaterguard.databinding.FragmentOnboarding2Binding

class OnboardingFragment2 : Fragment() {

    private var _binding: FragmentOnboarding2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate layout menggunakan View Binding
        _binding = FragmentOnboarding2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Atur teks menggunakan String Resources
        binding.onboardingTitle.text = getString(R.string.onboarding_title_2)
        binding.onboardingDescription.text = getString(R.string.onboarding_desc_2)
        binding.onboardingImage.contentDescription = getString(R.string.onboarding_image_desc_sensor)

        // Logika atau inisialisasi tambahan untuk Slide 2
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}