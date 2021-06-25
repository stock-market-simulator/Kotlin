package com.kunize.stock_market_simulator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kunize.stock_market_simulator.databinding.ItemRecyclerHistoryBinding
import com.kunize.stock_market_simulator.etcData.StockInfoFormat

class BuyingAdapter : RecyclerView.Adapter<BuyingHolder>() {
    var buyingData = mutableListOf<StockInfoFormat>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyingHolder {
        val binding = ItemRecyclerHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return BuyingHolder(binding)
    }

    override fun onBindViewHolder(holder: BuyingHolder, position: Int) {
        val data = buyingData[position]
        holder.setData(data)
    }

    override fun getItemCount(): Int {
        return buyingData.size
    }
}

class BuyingHolder(private val binding: ItemRecyclerHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setData(data: StockInfoFormat){
        binding.historyName.text = data.name
        binding.historyPrice.text = "${data.price}"
        binding.historyAmount.text = "${data.amount}"
        binding.historyTotal.text = "${data.total}"
    }
}