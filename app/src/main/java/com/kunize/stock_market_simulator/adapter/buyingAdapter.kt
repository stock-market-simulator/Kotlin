package com.kunize.stock_market_simulator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kunize.stock_market_simulator.databinding.ItemRecyclerBuyingBinding
import com.kunize.stock_market_simulator.etcData.StockInfoFormat

class buyingAdapter : RecyclerView.Adapter<buyingHolder>() {
    var buyingData = mutableListOf<StockInfoFormat>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): buyingHolder {
        val binding = ItemRecyclerBuyingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return buyingHolder(binding)
    }

    override fun onBindViewHolder(holder: buyingHolder, position: Int) {
        val data = buyingData[position]
        holder.setData(data)
    }

    override fun getItemCount(): Int {
        return buyingData.size
    }
}

class buyingHolder(private val binding: ItemRecyclerBuyingBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setData(data: StockInfoFormat){
        binding.buyName.text = data.name
        binding.buyPrice.text = "${data.price}"
        binding.buyAmount.text = "${data.amount}"
        binding.buyTotal.text = "${data.total}"
    }
}