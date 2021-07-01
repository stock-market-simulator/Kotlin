package com.kunize.stock_market_simulator.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kunize.stock_market_simulator.R
import com.kunize.stock_market_simulator.StockInfoActivity
import com.kunize.stock_market_simulator.adapter.HoldAdapter
import com.kunize.stock_market_simulator.adapter.HoldHolder
import com.kunize.stock_market_simulator.adapter.InterestAdapter
import com.kunize.stock_market_simulator.databinding.FragmentMyInfoBinding
import com.kunize.stock_market_simulator.etcData.StockInfoFormat


class MyInfoFragment : Fragment() {
    lateinit var binding: FragmentMyInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyInfoBinding.inflate(inflater, container,false)

        val holdAdapter = HoldAdapter()
        val tempData = mutableListOf(
            StockInfoFormat("네이버",400000,3,1200000,0.3f),
            StockInfoFormat("삼성전자",85000,10,850000, 0.5f),
            StockInfoFormat("삼성전자",85000,10,850000,10f),
            StockInfoFormat("삼성전자",85000,10,850000,-30f),
            StockInfoFormat("삼성전자",85000,10,850000,-10f)
        )
        holdAdapter.holdData = tempData
        binding.recyclerStocksHeld.adapter = holdAdapter
        binding.recyclerStocksHeld.layoutManager = LinearLayoutManager(activity)

        holdAdapter.setItemClickListener(object : HoldAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(activity, StockInfoActivity::class.java)
                intent.putExtra("stockName", holdAdapter.holdData[position].name)
                startActivity(intent)
            }
        })

        return binding.root
    }
}