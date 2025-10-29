// File: SensorData.kt
package com.raditya.smartwaterguard.model

data class SensorData(
    val type: SensorType,
    val value: Double,
    val unit: String,
    val iconResId: Int,
    val status: Status
)

enum class Status {
    SAFE, WARNING, DANGER
}