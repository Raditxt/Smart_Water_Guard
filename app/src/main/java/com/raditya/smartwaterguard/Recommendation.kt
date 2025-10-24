// File: Recommendation.kt
package com.raditya.smartwaterguard

data class Recommendation(
    val id: Int,
    val title: String,
    val description: String,
    val severity: Severity // Digunakan untuk menentukan warna ikon
)

enum class Severity {
    HIGH, MEDIUM, LOW
}