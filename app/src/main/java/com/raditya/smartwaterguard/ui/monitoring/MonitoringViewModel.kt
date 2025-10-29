package com.raditya.smartwaterguard.ui.monitoring

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raditya.smartwaterguard.model.SensorData
import com.raditya.smartwaterguard.model.SensorType
import com.raditya.smartwaterguard.model.Status
import com.raditya.smartwaterguard.utils.WaterStatusEvaluator
import com.raditya.smartwaterguard.R

class MonitoringViewModel : ViewModel() {

    private val _sensorData = MutableLiveData<List<SensorData>>()
    val sensorData: LiveData<List<SensorData>> get() = _sensorData

    init {
        loadDummyData()   // nanti tinggal ganti → loadFromRepository()
    }

    private fun loadDummyData() {
        val list = listOf(
            SensorData(SensorType.PH, 7.2, "", R.drawable.ic_ph, WaterStatusEvaluator.getStatus(7.2, SensorType.PH)),
            SensorData(SensorType.TDS, 185.0, "ppm", R.drawable.ic_tds, WaterStatusEvaluator.getStatus(185.0, SensorType.TDS)),
            SensorData(SensorType.TURBIDITY, 4.5, "NTU", R.drawable.ic_cloudy, WaterStatusEvaluator.getStatus(4.5, SensorType.TURBIDITY)),
            SensorData(SensorType.TEMP, 27.5, "°C", R.drawable.ic_temp, WaterStatusEvaluator.getStatus(27.5, SensorType.TEMP))
        )

        _sensorData.value = list
    }
}
