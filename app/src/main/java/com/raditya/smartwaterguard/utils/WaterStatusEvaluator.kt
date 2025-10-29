package com.raditya.smartwaterguard.utils

import com.raditya.smartwaterguard.model.SensorType
import com.raditya.smartwaterguard.model.Status

object WaterStatusEvaluator {

    fun getStatus(value: Double, type: SensorType): Status {
        return when (type) {
            SensorType.PH -> when {
                value < 6.5 || value > 8.5 -> Status.DANGER
                value < 7.0 || value > 8.0 -> Status.WARNING
                else -> Status.SAFE
            }

            SensorType.TDS -> when {
                value > 500 -> Status.DANGER
                value > 200 -> Status.WARNING
                else -> Status.SAFE
            }

            SensorType.TURBIDITY -> when {
                value > 10.0 -> Status.DANGER
                value > 5.0 -> Status.WARNING
                else -> Status.SAFE
            }

            SensorType.TEMP -> when {
                value > 30.0 -> Status.DANGER
                value > 28.0 -> Status.WARNING
                else -> Status.SAFE
            }
        }
    }
}
