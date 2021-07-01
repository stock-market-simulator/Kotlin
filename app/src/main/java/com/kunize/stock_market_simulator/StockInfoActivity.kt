package com.kunize.stock_market_simulator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.kunize.stock_market_simulator.MainActivity.Companion.RED
import com.kunize.stock_market_simulator.databinding.ActivityStockInfoBinding

class StockInfoActivity : AppCompatActivity() {
    private val binding by lazy { ActivityStockInfoBinding.inflate(layoutInflater) }
    private val spinnerData = listOf("1분","1일","1주일","1개월","3개월")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val spinnerAdapter = ArrayAdapter<String>(this,R.layout.item_spinner_chart_type,spinnerData)
        binding.spinnerChartType.adapter = spinnerAdapter

        val stockName = intent.getStringExtra("stockName")
        binding.transcationStockName.text = stockName

        binding.buyButton.setOnClickListener {
            val buyOrSellIntent = Intent(this, TransactionActivity::class.java)
            buyOrSellIntent.putExtra("stockName", stockName)
            buyOrSellIntent.putExtra("type","buy")
            startActivity(buyOrSellIntent)
        }

        binding.sellButton.setOnClickListener {
            val buyOrSellIntent = Intent(this, TransactionActivity::class.java)
            buyOrSellIntent.putExtra("stockName", stockName)
            buyOrSellIntent.putExtra("type","sell")
            startActivity(buyOrSellIntent)
        }

        chartSet(binding.stockChart)
        drawChart(binding.stockChart,RED)
    }

    private fun chartSet(chart: LineChart) {
        chart.apply {
            axisLeft.isEnabled = false //왼쪽 y축 노출
            axisRight.isEnabled = false //오른쪽 y축 노출
            xAxis.isEnabled = false //x축 노출
            xAxis.axisMinimum = 0f
            xAxis.axisMaximum = 100f
            legend.isEnabled = false //범례 노출
            description.isEnabled = false //설명 노출
            setTouchEnabled(true)
            setPinchZoom(false)
            isDoubleTapToZoomEnabled = false
        }
    }

    private fun drawChart(chart: LineChart, lineColor: Int) {
        val emptyInput = Array<Double>(30) { Math.random() }
        val entries: ArrayList<Entry> = ArrayList()
        for (i in 0 until 30) {
            entries.add(Entry(i.toFloat(), emptyInput[i].toFloat()))
        }
        for(i in 30 until 50) {
            entries.add(Entry(i.toFloat(), 0f))
        }
        for(i in 70 until 80) {
            entries.add(Entry(i.toFloat(), 0.5f))
        }
        val dataSet: LineDataSet = LineDataSet(entries, "코스피").apply {
            setDrawCircles(false)
            setDrawValues(false)
            color = lineColor //선 색상 변경
            lineWidth = 4f
        }

        val data = LineData(dataSet)
        chart.data = data
        chart.xAxis.setLabelCount(20,true)
        chart.invalidate()
    }
}