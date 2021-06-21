package com.kunize.stock_market_simulator.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.Color.rgb
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.red
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.kunize.stock_market_simulator.MainActivity.Companion.BLUE
import com.kunize.stock_market_simulator.MainActivity.Companion.RED
import com.kunize.stock_market_simulator.SearchActivity
import com.kunize.stock_market_simulator.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container,false)
        binding.searchButton.setOnClickListener {
            val intent = Intent(activity,SearchActivity::class.java)
            startActivity(intent)
        }

        drawKospiChart()
        drawKodaqChart()



        return binding.root
    }

    private fun drawKospiChart() {
        val emptyInput = Array<Double>(30) { Math.random() }
        val entries: ArrayList<Entry> = ArrayList()
        for (i in 0 until 30) {
            entries.add(Entry(i.toFloat(), emptyInput[i].toFloat()))
        }
        val dataSet: LineDataSet = LineDataSet(entries, "코스피").apply {
            setDrawCircles(false)
            setDrawValues(false)
            color = RED //선 색상 변경
            lineWidth = 2f
        }

        val data = LineData(dataSet)
        binding.kospiChart.data = data
        binding.kospiChart.invalidate()

        binding.kospiChart.apply {
            axisLeft.isEnabled = false //왼쪽 y축 노출
            axisRight.isEnabled = false //오른쪽 y축 노출
            xAxis.isEnabled = false //x축 노출
            xAxis.setDrawAxisLine(false)
            legend.isEnabled = false //범례 노출
            description.isEnabled = false //설명 노출
            description.textSize = 20f
            description.text = "코스피"
            description.textColor = rgb(117, 117, 117)
            setTouchEnabled(false)
        }
    }

    private fun drawKodaqChart() {
        val emptyInput = Array<Double>(30) { Math.random() }
        val entries: ArrayList<Entry> = ArrayList()
        for (i in 0 until 30) {
            entries.add(Entry(i.toFloat(), emptyInput[i].toFloat()))
        }
        val dataSet: LineDataSet = LineDataSet(entries, "input").apply {
            setDrawCircles(false)
            setDrawValues(false)
            color = BLUE //선 색상 변경
            lineWidth = 2f
        }

        binding.kosdaqChart.apply {
            axisLeft.isEnabled = false //왼쪽 y축 노출
            axisRight.isEnabled = false //오른쪽 y축 노출
            xAxis.isEnabled = false //x축 노출
            legend.isEnabled = false //범례 노출
            description.isEnabled = false //설명 노출
            setTouchEnabled(false)
        }

        val data = LineData(dataSet)
        binding.kosdaqChart.data = data
        binding.kosdaqChart.invalidate()
    }
}