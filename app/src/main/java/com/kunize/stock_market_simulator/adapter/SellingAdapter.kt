package com.kunize.stock_market_simulator.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kunize.stock_market_simulator.TransactionActivity
import com.kunize.stock_market_simulator.databinding.ItemRecyclerHistoryBinding
import com.kunize.stock_market_simulator.etcData.StockInfoFormat

class SellingAdapter : RecyclerView.Adapter<SellingHolder>() {
    var sellingData = mutableListOf<StockInfoFormat>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellingHolder {
        val binding = ItemRecyclerHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return SellingHolder(binding)
    }

    override fun onBindViewHolder(holder: SellingHolder, position: Int) {
        val data = sellingData[position]
        holder.setData(data)
        holder.edit.setOnClickListener {
            val intent = Intent(holder.itemView.context, TransactionActivity::class.java)
            intent.putExtra("stockName",holder.name.text)
            intent.putExtra("type","editSell")
            intent.putExtra("amount",holder.amount.text)
            ContextCompat.startActivity(holder.itemView.context,intent,null)
        }
        //delete 추가
    }

    override fun getItemCount(): Int {
        return sellingData.size
    }
}

class SellingHolder(private val binding: ItemRecyclerHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
    val edit = binding.historyEdit
    val delete = binding.historyDelete
    var name = binding.historyName
    var amount = binding.historyAmount
    fun setData(data: StockInfoFormat) {
        binding.historyName.text = data.name
        binding.historyPrice.text = "${data.price}"
        binding.historyAmount.text = "${data.amount}"
        binding.historyTotal.text = "${data.total}"
    }
}