package com.kunize.stock_market_simulator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kunize.stock_market_simulator.databinding.ActivityTranscationBinding

class TransactionActivity : AppCompatActivity() {
    private val binding by lazy { ActivityTranscationBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val stockName = intent.getStringExtra("stockName")
        binding.transcationStockName.text = stockName

        binding.buyButton.setOnClickListener {
            val buyOrSellIntent = Intent(this, BuyOrSellActivity::class.java)
            buyOrSellIntent.putExtra("stockName", stockName)
            buyOrSellIntent.putExtra("type","buy")
            startActivity(buyOrSellIntent)
        }

        binding.sellButton.setOnClickListener {
            val buyOrSellIntent = Intent(this, BuyOrSellActivity::class.java)
            buyOrSellIntent.putExtra("stockName", stockName)
            buyOrSellIntent.putExtra("type","sell")
            startActivity(buyOrSellIntent)
        }
    }
}