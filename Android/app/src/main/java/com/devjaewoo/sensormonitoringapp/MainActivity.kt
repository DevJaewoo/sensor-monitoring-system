package com.devjaewoo.sensormonitoringapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.devjaewoo.sensormonitoringapp.databinding.ActivityMainBinding
import com.devjaewoo.sensormonitoringapp.request.RequestHandler
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    private fun String.ISOStringToFloat(): Float {
        return this.substring(11, 19).replace(":", "").toFloat()
    }

    private val onSensorData = { response: JSONObject ->
        handler.obtainMessage(0, response).sendToTarget()
    }

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            val response = msg.obj as JSONObject

            val eco2 = ArrayList<Entry>()
            val tvoc = ArrayList<Entry>()
            val temp = ArrayList<Entry>()

            val accelX = ArrayList<Entry>()
            val accelY = ArrayList<Entry>()
            val accelZ = ArrayList<Entry>()

            val array = response.getJSONArray("sensorData")
            for(i in 0 until array.length()) {
                val row = array.getJSONObject(i)

                val date = row.getString("createdDate").ISOStringToFloat()
                eco2.add(Entry(date, row.getInt("eco2").toFloat()))
                tvoc.add(Entry(date, row.getInt("tvoc").toFloat()))
                temp.add(Entry(date, row.getDouble("temp").toFloat()))

                val accel = row.getJSONObject("accel")
                accelX.add(Entry(date, accel.getDouble("x").toFloat()))
                accelY.add(Entry(date, accel.getDouble("y").toFloat()))
                accelZ.add(Entry(date, accel.getDouble("z").toFloat()))
            }

            eco2.reverse()
            tvoc.reverse()
            temp.reverse()
            accelX.reverse()
            accelY.reverse()
            accelZ.reverse()

            binding.chartECO2.setData(eco2, "eCO2", "#7900FF")
            binding.chartTVOC.setData(tvoc, "TVOC", "#548CFF")
            binding.chartTemp.setData(temp, "Temperature", "#FF00BE")

            val lineDataSetX = LineDataSet(accelX, "X")
            lineDataSetX.color = Color.RED
            lineDataSetX.setCircleColor(Color.RED)
            lineDataSetX.circleHoleColor = ContextCompat.getColor(applicationContext, R.color.white)

            val lineDataSetY = LineDataSet(accelY, "Y")
            lineDataSetY.color = Color.GREEN
            lineDataSetY.setCircleColor(Color.GREEN)
            lineDataSetY.circleHoleColor = ContextCompat.getColor(applicationContext, R.color.white)

            val lineDataSetZ = LineDataSet(accelZ, "Z")
            lineDataSetZ.color = Color.BLUE
            lineDataSetZ.setCircleColor(Color.BLUE)
            lineDataSetZ.circleHoleColor = ContextCompat.getColor(applicationContext, R.color.white)

            binding.chartAccel.invalidate()
            binding.chartAccel.clear()
            binding.chartAccel.data = LineData().apply {
                addDataSet(lineDataSetX)
                addDataSet(lineDataSetY)
                addDataSet(lineDataSetZ)

                setValueTextColor(R.color.black)
                setValueTextSize(9f)
            }
        }
    }

    private fun LineChart.setData(data: ArrayList<Entry>, label: String, colorString: String) {

        val color = Color.parseColor(colorString)

        val lineDataSet = LineDataSet(data, label)
        lineDataSet.color = color
        lineDataSet.setCircleColor(color)
        lineDataSet.circleHoleColor = ContextCompat.getColor(applicationContext, R.color.white)

        val lineData = LineData().apply {
            addDataSet(lineDataSet)
            setValueTextColor(R.color.black)
            setValueTextSize(9f)
        }

        this.invalidate()
        this.clear()
        this.data = lineData
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        binding.chartECO2.initializeChart()
        binding.chartTVOC.initializeChart()
        binding.chartTemp.initializeChart()
        binding.chartAccel.initializeChart()

        GlobalScope.launch {
            while(true) {
                RequestHandler.request("/api/1/sensor?size=$CHART_SIZE", null, onSensorData, null, false, Request.Method.GET)
                Thread.sleep(2000)
            }
        }

        setContentView(binding.root)
    }

    private fun LineChart.initializeChart() {

        val colorGrid = Color.parseColor("#CCCCCC")
        val colorText = Color.parseColor("#666666")

        this.apply {
            invalidate()
            clear()
            description = null
        }

        this.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    val time = value.toInt()
                    return "${(time / 10000).toString().padStart(2, '0')}:" +
                            "${((time % 10000) / 100).toString().padStart(2, '0')}:" +
                            "${(time % 100).toString().padStart(2, '0')}"
                }
            }

            setLabelCount(CHART_SIZE, true)
            textColor = colorText
            gridColor = colorGrid
        }

        this.axisLeft.apply {
            textColor = colorText
            gridColor = colorGrid
        }

        this.axisRight.apply {
            setDrawLabels(false)
            setDrawAxisLine(false)
            setDrawGridLines(false)
        }

        this.legend.apply {
            textColor = colorText
        }
    }
}