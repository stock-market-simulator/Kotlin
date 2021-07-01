package com.kunize.stock_market_simulator.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kunize.stock_market_simulator.MainActivity.Companion.BLUE
import com.kunize.stock_market_simulator.MainActivity.Companion.RED
import com.kunize.stock_market_simulator.TransactionActivity.Companion.decimalFormat
import com.kunize.stock_market_simulator.databinding.ItemRecyclerInterestBinding
import com.kunize.stock_market_simulator.etcData.interestFormat

class InterestAdapter : RecyclerView.Adapter<InterestHolder>() {
    var interestData = mutableListOf<interestFormat>()
    private lateinit var itemClickListner: ItemClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestHolder {
        val binding = ItemRecyclerInterestBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return InterestHolder(binding)
    }

    override fun getItemCount(): Int {
        return interestData.size
    }

    override fun onBindViewHolder(holder: InterestHolder, position: Int) {
        val data = interestData[position]
        holder.setData(data)
        holder.itemView.setOnClickListener{
            itemClickListner.onClick(it,position)
        }
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }
}

class InterestHolder(private val binding: ItemRecyclerInterestBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setData(data: interestFormat){
        binding.InterestName.text = data.name
        binding.InterestPrice.text = decimalFormat.format(data.price)
        var rateFormat = ""
        when {
            data.rate > 0 -> {
                binding.arrow.setTextColor(RED)
                binding.arrow.text = "▲"
                binding.InterestRate.setTextColor(RED)
                binding.InterestPrice.setTextColor(RED)
                rateFormat = "+"
            }
            data.rate < 0 -> {
                binding.arrow.setTextColor(BLUE)
                binding.arrow.text = "▼"
                binding.InterestRate.setTextColor(BLUE)
                binding.InterestPrice.setTextColor(BLUE)
            }
            else -> binding.arrow.text = "-"
        }
        rateFormat += "${data.rate}%"
        binding.InterestRate.text = rateFormat
    }
}