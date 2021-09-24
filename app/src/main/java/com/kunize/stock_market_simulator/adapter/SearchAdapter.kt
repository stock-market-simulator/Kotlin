package com.kunize.stock_market_simulator.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.kunize.stock_market_simulator.HangulUtils
import com.kunize.stock_market_simulator.databinding.ItemRecyclerSearchBinding
import java.util.*

class SearchAdapter : RecyclerView.Adapter<SearchHolder>(), Filterable {
    var filteredData = mutableListOf<String>()
    var unfilteredData = mutableListOf<String>()
    private lateinit var itemClickListner: ItemClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val binding = ItemRecyclerSearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return SearchHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredData.size
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        val data = filteredData[position]
        holder.setData(data)
        holder.itemView.setOnClickListener{
            itemClickListner.onClick(it,position)
        }
    }

    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                filteredData = if(charString.isEmpty()) {
                    unfilteredData
                } else {
                    val filteredList = mutableListOf<String>()
                    for(name in unfilteredData){
                        val iniName = HangulUtils.getHangulInitialSound(name, charString);
                        if (iniName.indexOf(charString) >= 0) { // 초성검색어가 있으면 해당 데이터 리스트에 추가
                            filteredList.add(name);
                        }
                        else if (name.lowercase(Locale.ROOT).contains(charString.lowercase(Locale.ROOT))) {
                            filteredList.add(name)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredData
                return filterResults
            }
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                filteredData  = results.values as MutableList<String>
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