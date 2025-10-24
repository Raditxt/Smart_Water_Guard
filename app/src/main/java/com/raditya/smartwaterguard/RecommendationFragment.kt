// File: RecommendationFragment.kt
package com.raditya.smartwaterguard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.raditya.smartwaterguard.databinding.FragmentRecommendationBinding

class RecommendationFragment : Fragment() {

    private var _binding: FragmentRecommendationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecommendationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recommendations = createDummyRecommendations()

        // Inisialisasi RecyclerView
        val adapter = RecommendationAdapter(recommendations)
        binding.recyclerRecommendations.adapter = adapter
        // LayoutManager sudah didefinisikan di XML (LinearLayoutManager)
    }

    private fun createDummyRecommendations(): List<Recommendation> {
        return listOf(
            Recommendation(
                1,
                "Lakukan Uji pH Eksternal",
                "Nilai pH mendekati batas minimal (6.5). Ulangi pengujian dengan alat eksternal untuk konfirmasi sebelum mengambil tindakan korektif.",
                Severity.HIGH
            ),
            Recommendation(
                2,
                "Bersihkan Sensor TDS",
                "Peringatan TDS tinggi mungkin disebabkan oleh penumpukan mineral pada probe sensor. Matikan alat dan bersihkan sensor menggunakan air suling.",
                Severity.MEDIUM
            ),
            Recommendation(
                3,
                "Periksa Filter Sedimen",
                "Peningkatan Turbidity (kekeruhan) terdeteksi. Periksa filter sedimen utama. Mungkin sudah waktunya untuk penggantian atau pembersihan.",
                Severity.MEDIUM
            ),
            Recommendation(
                4,
                "Jadwalkan Penggantian Air",
                "Meskipun aman, air sudah berusia lama. Ganti 25% volume air untuk menjaga kesegaran dan mineral yang seimbang.",
                Severity.LOW
            ),
            Recommendation(
                5,
                "Ukur Suhu Puncak",
                "Suhu air (28°C) berada dalam batas normal. Catat suhu air pada waktu terpanas di siang hari untuk memantau fluktuasi termal.",
                Severity.LOW
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}