package com.raditya.smartwaterguard

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.raditya.smartwaterguard.databinding.FragmentMonitoringBinding
import com.raditya.smartwaterguard.databinding.CardSensorItemBinding

// IMPORTS MPANDROIDCHART
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter // Menggantikan IndexAxisFormatter


class MonitoringFragment : Fragment() {

    private var _binding: FragmentMonitoringBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMonitoringBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. DUMMY DATA SENSOR
        val sensorDataList = listOf(
            SensorData("pH", 7.2, "", R.drawable.ic_ph, determineStatus(7.2, "pH")),
            SensorData("TDS", 185.0, "ppm", R.drawable.ic_tds, determineStatus(185.0, "TDS")),
            SensorData("Turbidity", 4.5, "NTU", R.drawable.ic_cloudy, determineStatus(4.5, "Turbidity")),
            SensorData("Temperature", 27.5, "°C", R.drawable.ic_temp, determineStatus(27.5, "Temp"))
        )

        // 2. Hubungkan data ke setiap CardView
        bindSensorData(binding.includePh, sensorDataList[0])
        bindSensorData(binding.includeTds, sensorDataList[1])
        bindSensorData(binding.includeTurbidity, sensorDataList[2])
        bindSensorData(binding.includeTemp, sensorDataList[3])

        // 3. Setup Line Chart
        setupLineChart(binding.lineChartHistory)
    }

    // ===============================================
    // FUNGSI LOGIKA SENSOR
    // ===============================================

    private fun determineStatus(value: Double, parameter: String): Status {
        return when (parameter) {
            "pH" -> when {
                value < 6.5 || value > 8.5 -> Status.DANGER
                value < 7.0 || value > 8.0 -> Status.WARNING
                else -> Status.SAFE
            }
            "TDS" -> when {
                value > 500 -> Status.DANGER
                value > 200 -> Status.WARNING
                else -> Status.SAFE
            }
            "Turbidity" -> when {
                value > 10.0 -> Status.DANGER
                value > 5.0 -> Status.WARNING
                else -> Status.SAFE
            }
            "Temp" -> when {
                value > 30.0 -> Status.DANGER
                value > 28.0 -> Status.WARNING
                else -> Status.SAFE
            }
            else -> Status.SAFE
        }
    }

    private fun bindSensorData(cardBinding: CardSensorItemBinding, data: SensorData) {
        cardBinding.tvParameterName.text = data.name
        cardBinding.tvParameterValue.text = data.value.toString()
        cardBinding.tvParameterUnit.text = data.unit
        cardBinding.ivIcon.setImageResource(data.iconResId)

        val context = requireContext()

        when (data.status) {
            Status.SAFE -> {
                cardBinding.tvStatusIndicator.text = "Aman"
                cardBinding.tvStatusIndicator.background.setTint(ContextCompat.getColor(context, R.color.green_healthy))
            }
            Status.WARNING -> {
                cardBinding.tvStatusIndicator.text = "Waspada"
                cardBinding.tvStatusIndicator.background.setTint(ContextCompat.getColor(context, R.color.amber_warning))
            }
            Status.DANGER -> {
                cardBinding.tvStatusIndicator.text = "Bahaya"
                cardBinding.tvStatusIndicator.background.setTint(ContextCompat.getColor(context, R.color.red_danger))
            }
        }
    }

    // ===============================================
    // FUNGSI LOGIKA CHART
    // ===============================================

    private fun getDummyHistoryData(): List<Entry> {
        // Simulasi 7 data poin (nilai pH 7 jam terakhir)
        return listOf(
            Entry(0f, 6.8f),
            Entry(1f, 7.0f),
            Entry(2f, 7.1f),
            Entry(3f, 7.3f),
            Entry(4f, 7.2f),
            Entry(5f, 6.9f),
            Entry(6f, 7.0f) // Nilai pH yang sedikit berubah-ubah
        )
    }

    private fun setupLineChart(chart: LineChart) {
        val entries = getDummyHistoryData()

        val dataSet = LineDataSet(entries, "pH Air (7 Jam Terakhir)").apply {
            color = ContextCompat.getColor(requireContext(), R.color.primary_blue)
            lineWidth = 3f
            setDrawCircles(true)
            setCircleColor(ContextCompat.getColor(requireContext(), R.color.light_blue))
            circleRadius = 5f
            setDrawValues(false)
            mode = LineDataSet.Mode.CUBIC_BEZIER
        }

        val lineData = LineData(dataSet)
        chart.data = lineData

        // Konfigurasi Sumbu X
        chart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
            textColor = ContextCompat.getColor(requireContext(), R.color.deep_navy)
            // Menggunakan IndexAxisValueFormatter untuk label kustom
            valueFormatter = IndexAxisValueFormatter(arrayOf("7h", "6h", "5h", "4h", "3h", "2h", "Sekarang"))
        }

        // Konfigurasi Sumbu Y Kiri
        chart.axisLeft.apply {
            textColor = ContextCompat.getColor(requireContext(), R.color.deep_navy)
            axisMinimum = 6.0f
            axisMaximum = 8.0f
            gridColor = ContextCompat.getColor(requireContext(), R.color.soft_gray_ai)
        }

        // Konfigurasi Umum Chart
        chart.description.isEnabled = false
        chart.legend.isEnabled = true
        chart.axisRight.isEnabled = false
        chart.setTouchEnabled(true)
        chart.isDragEnabled = true
        chart.setScaleEnabled(false)
        chart.animateX(1500)
        chart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}