// File: OnboardingFragment3.kt
package com.raditya.smartwaterguard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.raditya.smartwaterguard.databinding.FragmentOnboarding3Binding

class OnboardingFragment3 : Fragment() {

    private var _binding: FragmentOnboarding3Binding? = null
    // Properti non-null untuk memudahkan akses
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate layout menggunakan View Binding
        _binding = FragmentOnboarding3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Atur teks menggunakan String Resources
        binding.onboardingTitle.text = getString(R.string.onboarding_title_3)
        binding.onboardingDescription.text = getString(R.string.onboarding_desc_3)
        binding.onboardingImage.contentDescription = getString(R.string.onboarding_image_desc_join)

        // Tombol 'btn_get_started' telah dihapus dari XML, sehingga tidak ada lagi kode di sini
        // yang mencoba mengaksesnya, mencegah crash.
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Penting: Hapus referensi binding untuk mencegah memory leak
        _binding = null
    }
}