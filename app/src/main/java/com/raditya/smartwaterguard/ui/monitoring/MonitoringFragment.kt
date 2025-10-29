package com.raditya.smartwaterguard.ui.monitoring

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raditya.smartwaterguard.databinding.FragmentMonitoringBinding
import com.raditya.smartwaterguard.databinding.CardSensorItemBinding
import com.raditya.smartwaterguard.model.SensorData
import com.raditya.smartwaterguard.model.SensorType
import com.raditya.smartwaterguard.model.Status
import com.raditya.smartwaterguard.R

// CHART IMPORT
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class MonitoringFragment : Fragment() {

    private var _binding: FragmentMonitoringBinding? = null
    private val binding get() = _binding!!

    // ViewModel
    private val viewModel: MonitoringViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMonitoringBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeSensorData()
        setupLineChart(binding.lineChartHistory)
    }

    // =========================================================
    // OBSERVE DATA
    // =========================================================
    private fun observeSensorData() {
        viewModel.sensorData.observe(viewLifecycleOwner) { list ->
            list.forEach { data ->
                when (data.type) {
                    SensorType.PH -> bindSensorData(binding.includePh, data)
                    SensorType.TDS -> bindSensorData(binding.includeTds, data)
                    SensorType.TURBIDITY -> bindSensorData(binding.includeTurbidity, data)
                    SensorType.TEMP -> bindSensorData(binding.includeTemp, data)
                }
            }
        }
    }

    // =========================================================
    // BINDING CARD SENSOR
    // =========================================================
    private fun bindSensorData(cardBinding: CardSensorItemBinding, data: SensorData) {
        val ctx = requireContext()

        cardBinding.tvParameterName.text = data.type.toString()
        cardBinding.tvParameterValue.text = data.value.toString()
        cardBinding.tvParameterUnit.text = data.unit
        cardBinding.ivIcon.setImageResource(data.iconResId)

        when (data.status) {
            Status.SAFE -> {
                cardBinding.tvStatusIndicator.text = "Aman"
                cardBinding.tvStatusIndicator.background.setTint(
                    ContextCompat.getColor(ctx, R.color.green_healthy)
                )
            }

            Status.WARNING -> {
                cardBinding.tvStatusIndicator.text = "Waspada"
                cardBinding.tvStatusIndicator.background.setTint(
                    ContextCompat.getColor(ctx, R.color.amber_warning)
                )
            }

            Status.DANGER -> {
                cardBinding.tvStatusIndicator.text = "Bahaya"
                cardBinding.tvStatusIndicator.background.setTint(
                    ContextCompat.getColor(ctx, R.color.red_danger)
                )
            }
        }
    }

    // =========================================================
    // CHART
    // =========================================================
    private fun getDummyHistoryData(): List<Entry> {
        return listOf(
            Entry(0f, 6.8f),
            Entry(1f, 7.0f),
            Entry(2f, 7.1f),
            Entry(3f, 7.3f),
            Entry(4f, 7.2f),
            Entry(5f, 6.9f),
            Entry(6f, 7.0f)
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

        chart.data = LineData(dataSet)

        // X Axis
        chart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
            textColor = ContextCompat.getColor(requireContext(), R.color.deep_navy)
            valueFormatter = IndexAxisValueFormatter(
                arrayOf("7h", "6h", "5h", "4h", "3h", "2h", "Now")
            )
        }

        // Y Axis
        chart.axisLeft.apply {
            textColor = ContextCompat.getColor(requireContext(), R.color.deep_navy)
            axisMinimum = 6.0f
            axisMaximum = 8.5f
            gridColor = ContextCompat.getColor(requireContext(), R.color.soft_gray_ai)
        }

        chart.axisRight.isEnabled = false
        chart.description.isEnabled = false
        chart.legend.isEnabled = true
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
