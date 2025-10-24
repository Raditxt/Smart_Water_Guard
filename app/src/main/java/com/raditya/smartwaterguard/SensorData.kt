// File: SensorData.kt
package com.raditya.smartwaterguard

data class SensorData(
    val name: String,
    val value: Double,
    val unit: String,
    val iconResId: Int,
    val status: Status
)

enum class Status {
    SAFE, WARNING, DANGER
}