package com.kunize.stock_market_simulator.fragment

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.github.mikephil.charting.charts.LineChart
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
import com.kunize.stock_market_simulator.server.ApiInterface
import com.kunize.stock_market_simulator.server.DTO.Bookmark
import com.kunize.stock_market_simulator.server.DTO.KospiKosdaq
import com.kunize.stock_market_simulator.server.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private lateinit var api: ApiInterface
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container,false)
        binding.searchButton.setOnClickListener {
            val intent = Intent(activity,SearchActivity::class.java)
            startActivity(intent)
        }

        val retrofit = RetrofitClient.getInstance()
        api = retrofit.create(ApiInterface::class.java)

        api.requestKospiKosdaq().enqueue(object : Callback<KospiKosdaq> {
            override fun onFailure(call: Call<KospiKosdaq>, t: Throwable) {
                Log.d("retrofit","${t.message}")
            }

            override fun onResponse(
                call: Call<KospiKosdaq>,
                response: Response<KospiKosdaq>
            ) {
                val result : KospiKosdaq? = response.body()
                Log.d("retrofit","${result?.getKosdaq()}")
                chartSet(binding.kospiChart)
                chartSet(binding.kosdaqChart)
                drawChart(binding.kospiChart, RED, result!!.getKospi())
                drawChart(binding.kosdaqChart, BLUE, result.getKosdaq())
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val userId = requireActivity().getSharedPreferences("userID", MODE_PRIVATE).getString("userID","")

        api.getBookmark(userId!!).enqueue(object  : Callback<Bookmark> {
            override fun onResponse(call: Call<Bookmark>, response: Response<Bookmark>) {
                if(response.isSuccessful) {
                    val result = response.body()
                    if(result != null) {
                        val tempData = result.getBookmarkByInterestFormat()
                        val interestAdapter = InterestAdapter()

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
                        Log.d("bookmark","$userId")
                    }
                }
            }

            override fun onFailure(call: Call<Bookmark>, t: Throwable) {
                Log.d("bookmarkTest","북마크 불러오기 실패 ${t.message}")
            }
        })
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

    private fun drawChart(chart: LineChart, lineColor: Int, data: MutableList<Double>) {
        val entries: ArrayList<Entry> = ArrayList()
        for (i in 0 until data.size) {
            entries.add(Entry(i.toFloat(), data[i].toFloat()))
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