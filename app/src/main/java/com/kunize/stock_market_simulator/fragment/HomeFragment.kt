package com.kunize.stock_market_simulator.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.Color.rgb
import android.graphics.ColorSpace
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.red
import androidx.recyclerview.widget.GridLayoutManager
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.kunize.stock_market_simulator.MainActivity.Companion.BLUE
import com.kunize.stock_market_simulator.MainActivity.Companion.RED
import com.kunize.stock_market_simulator.SearchActivity
import com.kunize.stock_market_simulator.StockInfoActivity
import com.kunize.stock_market_simulator.adapter.InterestAdapter
import com.kunize.stock_market_simulator.databinding.FragmentHomeBinding
import com.kunize.stock_market_simulator.etcData.interestFormat

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container,false)
        binding.searchButton.setOnClickListener {
            val intent = Intent(activity,SearchActivity::class.java)
            startActivity(intent)
        }

        chartSet(binding.kospiChart)
        chartSet(binding.kosdaqChart)
        drawChart(binding.kospiChart, RED)
        drawChart(binding.kosdaqChart, BLUE)

        val interestAdapter = InterestAdapter()
        val tempData = mutableListOf<interestFormat>(
            interestFormat("삼성전자",900,2.3),
            interestFormat("대한항공",3100,-3.5),
            interestFormat("네이버",419000,-1.06),
            interestFormat("삼성전자",9000000,2.3),
            interestFormat("대한항공",31000000,-3.5),
            interestFormat("삼성전자",90000,2.3),
            interestFormat("대한항공",31000,-3.5),
            interestFormat("삼성전자",90000,2.3),
            interestFormat("대한항공",31000,-3.5),
            interestFormat("삼성전자",90000,2.3),
            interestFormat("대한항공",31000,-3.5)
        )
        interestAdapter.interestData = tempData
        binding.recyclerInterest.adapter = interestAdapter
        binding.recyclerInterest.layoutManager = GridLayoutManager(activity,2)

        interestAdapter.setItemClickListener(object : InterestAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(activity, StockInfoActivity::class.java)
                intent.putExtra("stockName", interestAdapter.interestData[position].name)
                startActivity(intent)
            }
        })


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun chartSet(chart: LineChart) {
        chart.apply {
            axisLeft.isEnabled = false //왼쪽 y축 노출
            axisRight.isEnabled = false //오른쪽 y축 노출
            xAxis.isEnabled = false //x축 노출
            legend.isEnabled = false //범례 노출
            description.isEnabled = false //설명 노출
            setTouchEnabled(false)
        }
    }

    private fun drawChart(chart: LineChart, lineColor: Int) {
        val emptyInput = Array<Double>(30) { Math.random() }
        val entries: ArrayList<Entry> = ArrayList()
        for (i in 0 until 30) {
            entries.add(Entry(i.toFloat(), emptyInput[i].toFloat()))
        }
        val dataSet: LineDataSet = LineDataSet(entries, "코스피").apply {
            setDrawCircles(false)
            setDrawValues(false)
            color = lineColor //선 색상 변경
            lineWidth = 2f
        }

        val data = LineData(dataSet)
        chart.data = data
        chart.invalidate()
    }
}