package com.kunize.stock_market_simulator.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kunize.stock_market_simulator.SearchActivity
import com.kunize.stock_market_simulator.adapter.BuyingAdapter
import com.kunize.stock_market_simulator.adapter.SellingAdapter
import com.kunize.stock_market_simulator.databinding.FragmentTransactionBinding
import com.kunize.stock_market_simulator.etcData.StockInfoFormat

class TransactionHistoryFragment : Fragment() {
    lateinit var binding: FragmentTransactionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buyingAdapter = BuyingAdapter()
        val tempData = mutableListOf(
            StockInfoFormat("삼성전자",85000,10,850000),
            StockInfoFormat("네이버",419000,2,419000*2),
            StockInfoFormat("삼성전자우",30000,3,90000),
            StockInfoFormat("카카오",50000,4,200000),
            StockInfoFormat("대한항공",31000,5,155000)
        )
        buyingAdapter.buyingData = tempData
        binding.recyclerbuying.adapter = buyingAdapter
        binding.recyclerbuying.layoutManager = LinearLayoutManager(activity)

        val sellingAdapter = SellingAdapter()
        val tempData2 = mutableListOf(
            StockInfoFormat("네이버",400000,3,1200000),
            StockInfoFormat("삼성전자",85000,10,850000),
            StockInfoFormat("삼성전자",85000,10,850000),
            StockInfoFormat("삼성전자",85000,10,850000),
            StockInfoFormat("삼성전자",85000,10,850000)
        )
        sellingAdapter.sellingData = tempData2
        binding.recyclerselling.adapter = sellingAdapter
        binding.recyclerselling.layoutManager = LinearLayoutManager(activity)
    }
}