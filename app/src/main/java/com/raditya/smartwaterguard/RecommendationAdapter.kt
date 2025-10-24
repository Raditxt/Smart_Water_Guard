// File: RecommendationAdapter.kt
package com.raditya.smartwaterguard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.raditya.smartwaterguard.databinding.ItemRecommendationBinding

class RecommendationAdapter(private val recommendations: List<Recommendation>) :
    RecyclerView.Adapter<RecommendationAdapter.RecommendationViewHolder>() {

    inner class RecommendationViewHolder(private val binding: ItemRecommendationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recommendation: Recommendation) {
            binding.tvRecommendationTitle.text = recommendation.title
            binding.tvRecommendationDescription.text = recommendation.description

            // Atur warna ikon berdasarkan Severity
            val context = binding.root.context
            when (recommendation.severity) {
                Severity.HIGH -> {
                    binding.ivSeverityIcon.setImageResource(R.drawable.ic_alert) // Ikon peringatan
                    binding.ivSeverityIcon.setColorFilter(ContextCompat.getColor(context, R.color.red_danger))
                }
                Severity.MEDIUM -> {
                    binding.ivSeverityIcon.setImageResource(R.drawable.ic_warning) // Ikon waspada
                    binding.ivSeverityIcon.setColorFilter(ContextCompat.getColor(context, R.color.amber_warning))
                }
                Severity.LOW -> {
                    binding.ivSeverityIcon.setImageResource(R.drawable.ic_check_circle) // Ikon saran
                    binding.ivSeverityIcon.setColorFilter(ContextCompat.getColor(context, R.color.green_healthy))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        val binding = ItemRecommendationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecommendationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        holder.bind(recommendations[position])
    }

    override fun getItemCount() = recommendations.size
}