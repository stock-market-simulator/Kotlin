package com.kunize.stock_market_simulator.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.kunize.stock_market_simulator.databinding.ItemRecyclerSearchBinding
import java.util.*

class SearchAdapter : RecyclerView.Adapter<SearchHolder>(), Filterable {
    var listData = mutableListOf<String>()
    var listData2 = mutableListOf<String>()
    private lateinit var itemClickListner: ItemClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val binding = ItemRecyclerSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        return SearchHolder(binding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        val data = listData[position]
        holder.setData(data)
        holder.itemView.setOnClickListener{
            itemClickListner.onClick(it,position)
        }
    }

    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                listData = if(charString.isEmpty()) {
                    listData2
                } else {
                    val filteredList = mutableListOf<String>()
                    for(name in listData2){
                        if (name.toLowerCase(Locale.ROOT).contains(charString.toLowerCase(Locale.ROOT))) {
                            filteredList.add(name)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = listData
                return filterResults
            }
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                listData  = results.values as MutableList<String>
                notifyDataSetChanged()
            }
        }
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }
}

class SearchHolder(private val binding: ItemRecyclerSearchBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setData(data: String){
        binding.stockName.text = data
    }
}