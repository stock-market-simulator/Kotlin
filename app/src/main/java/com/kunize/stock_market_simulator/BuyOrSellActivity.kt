package com.kunize.stock_market_simulator

import android.graphics.Color.rgb
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.kunize.stock_market_simulator.databinding.ActivityBuyOrSellBinding

class BuyOrSellActivity : AppCompatActivity() {
    private val binding by lazy { ActivityBuyOrSellBinding.inflate(layoutInflater) }
    private val spinnerData = listOf("시장가","지정가")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val spinnerAdapter = ArrayAdapter<String>(this,R.layout.item_spinner,spinnerData)
        binding.spinner.adapter = spinnerAdapter

        val stockName = intent.getStringExtra("stockName")
        val type = intent.getStringExtra("type")

        binding.buyOrSellStockName.text = stockName
        when (type) {
            "buy" -> {
                binding.buttonTranscation.text = "구매"
                binding.textBuyorSell.text = "구매합니다."
            }
            "sell" -> {
                binding.buttonTranscation.text = "판매"
                binding.textBuyorSell.text = "판매합니다."
            }
            else -> {
                //정정 부분 추가
            }
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position){
                    0-> {
                        binding.editPrice.inputType = InputType.TYPE_NULL
                        binding.editPrice.hint = "90,000"
                        binding.editPrice.setHintTextColor(rgb(46, 204, 113))
                    }
                    1-> {
                        binding.editPrice.inputType = InputType.TYPE_CLASS_NUMBER
                        binding.editPrice.hint = "가격"
                        binding.editPrice.setHintTextColor(rgb(190, 190, 190))
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.editPrice.inputType = InputType.TYPE_NULL
            }
        }
    }
}