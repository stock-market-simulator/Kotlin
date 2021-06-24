package com.kunize.stock_market_simulator.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kunize.stock_market_simulator.SearchActivity
import com.kunize.stock_market_simulator.adapter.buyingAdapter
import com.kunize.stock_market_simulator.databinding.FragmentTransactionBinding
import com.kunize.stock_market_simulator.etcData.StockInfoFormat

class TransactionHistoryFragment : Fragment() {
    lateinit var binding: FragmentTransactionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransactionBinding.inflate(inflater, container, false)
        binding.searchButton.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buyingAdapter = buyingAdapter()
        val tempData = mutableListOf(
            StockInfoFormat("삼성전자",85000,10,850000)
        )
        buyingAdapter.buyingData = tempData
        binding.recyclerbuying.adapter = buyingAdapter
        binding.recyclerbuying.layoutManager = LinearLayoutManager(activity)
    }
}