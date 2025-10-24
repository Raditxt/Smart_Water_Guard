// File: AIFragment.kt
package com.raditya.smartwaterguard

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.raditya.smartwaterguard.databinding.FragmentAiBinding
import java.util.Random

class AIFragment : Fragment() {

    private var _binding: FragmentAiBinding? = null
    private val binding get() = _binding!!

    // Status global yang akan diuji
    private enum class GlobalStatus { SAFE, WARNING, DANGER }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRunAnalysis.setOnClickListener {
            runAnalysisSimulation()
        }

        // Sembunyikan hasil saat fragment dimuat pertama kali
        binding.cardAnalysisResult.visibility = View.GONE
    }

    private fun runAnalysisSimulation() {

        // 1. Tampilkan loading dan sembunyikan hasil sebelumnya
        binding.lottieLoading.visibility = View.VISIBLE
        binding.btnRunAnalysis.isEnabled = false // Nonaktifkan tombol
        binding.cardAnalysisResult.visibility = View.GONE

        // 2. Simulasikan proses analisis 3 detik
        Handler(Looper.getMainLooper()).postDelayed({

            // 3. Tentukan hasil analisis acak (untuk demonstrasi)
            val randomResult = GlobalStatus.entries[Random().nextInt(GlobalStatus.entries.size)]

            // 4. Tampilkan hasil
            displayAnalysisResult(randomResult)

            // 5. Sembunyikan loading dan aktifkan tombol kembali
            binding.lottieLoading.visibility = View.GONE
            binding.btnRunAnalysis.isEnabled = true
            binding.cardAnalysisResult.visibility = View.VISIBLE

        }, 3000) // Penundaan 3 detik
    }

    private fun displayAnalysisResult(status: GlobalStatus) {
        val context = requireContext()

        when (status) {
            GlobalStatus.SAFE -> {
                binding.tvGlobalStatus.text = "LAYAK DIKONSUMSI"
                binding.tvGlobalStatus.setTextColor(ContextCompat.getColor(context, R.color.green_healthy))
                binding.tvAiSummary.text = "Selamat! Analisis AI mengonfirmasi bahwa semua parameter air berada dalam batas aman. Kualitas air Anda optimal dan aman untuk semua keperluan."
            }
            GlobalStatus.WARNING -> {
                binding.tvGlobalStatus.text = "PERLU PERHATIAN"
                binding.tvGlobalStatus.setTextColor(ContextCompat.getColor(context, R.color.amber_warning))
                binding.tvAiSummary.text = "Perhatian. Analisis AI mendeteksi satu atau lebih parameter mendekati batas toleransi (misalnya TDS tinggi). Disarankan untuk memeriksa tab Rekomendasi."
            }
            GlobalStatus.DANGER -> {
                binding.tvGlobalStatus.text = "TIDAK LAYAK"
                binding.tvGlobalStatus.setTextColor(ContextCompat.getColor(context, R.color.red_danger))
                binding.tvAiSummary.text = "BAHAYA! Analisis AI mendeteksi parameter yang jauh di luar batas aman (misalnya pH terlalu rendah). Jangan dikonsumsi dan segera periksa sumber air."
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}