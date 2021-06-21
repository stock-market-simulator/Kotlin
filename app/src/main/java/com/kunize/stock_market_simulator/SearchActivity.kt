package com.kunize.stock_market_simulator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.kunize.stock_market_simulator.adapter.SearchAdapter
import com.kunize.stock_market_simulator.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val searchAdapter = SearchAdapter()
        val tempData = mutableListOf<String>("삼성전자", "삼성전자우", "네이버", "카카오", "대한항공")
        searchAdapter.filteredData = tempData
        searchAdapter.unfilteredData = tempData

        binding.recyclerStockNames.adapter = searchAdapter
        binding.recyclerStockNames.layoutManager = LinearLayoutManager(this)

        binding.cardView.visibility = View.INVISIBLE

        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Do Nothing
            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchAdapter.filter?.filter(charSequence)
            }

            override fun afterTextChanged(charSequence: Editable?) {
                Log.d("searchTest", "${searchAdapter.itemCount}, ${binding.searchInput.text.toString()}")
                if (binding.searchInput.text.toString() == "") binding.cardView.visibility = View.INVISIBLE
                else binding.cardView.visibility = View.VISIBLE
            }
        })

        searchAdapter.setItemClickListener(object : SearchAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int) {
                //여기에서 거래 액티비티 실행
                val intent = Intent(this@SearchActivity, TransactionActivity::class.java)
                intent.putExtra("stockName", searchAdapter.filteredData[position])
                startActivity(intent)
            }
        })

    }
}