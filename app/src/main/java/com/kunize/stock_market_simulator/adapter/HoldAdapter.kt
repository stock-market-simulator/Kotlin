package com.kunize.stock_market_simulator.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kunize.stock_market_simulator.MainActivity.Companion.BLUE
import com.kunize.stock_market_simulator.MainActivity.Companion.RED
import com.kunize.stock_market_simulator.databinding.ItemRecyclerHoldBinding
import com.kunize.stock_market_simulator.etcData.StockInfoFormat

class HoldAdapter : RecyclerView.Adapter<HoldHolder>() {
    var holdData = mutableListOf<StockInfoFormat>()
    private lateinit var itemClickListner: ItemClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoldHolder {
        val binding = ItemRecyclerHoldBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return HoldHolder(binding)
    }

    override fun onBindViewHolder(holder: HoldHolder, position: Int) {
        val data = holdData[position]
        holder.setData(data)
        holder.itemView.setOnClickListener{
            itemClickListner.onClick(it,position)
        }
    }

    override fun getItemCount(): Int {
        return holdData.size
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }
}

class HoldHolder(private val binding: ItemRecyclerHoldBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setData(data: StockInfoFormat) {
        binding.holdName.text = data.name
        binding.averPrice.text = "${data.price}"
        binding.amount.text = "${data.amount}"
        binding.total.text = "${data.total}"
        var rateText = "("
        if(data.rate > 0){
            binding.rate.setTextColor(RED)
            rateText += "+"
        } else if(data.rate < 0){
            binding.rate.setTextColor(BLUE)
        }
        rateText += "${data.rate}%)"
        binding.rate.text = rateText
    }
}