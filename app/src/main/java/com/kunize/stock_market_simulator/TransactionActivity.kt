package com.kunize.stock_market_simulator

import android.graphics.Color.rgb
import android.os.Bundle
import android.text.InputType
import android.text.TextPaint
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.kunize.stock_market_simulator.databinding.ActivityTransactionBinding


class TransactionActivity : AppCompatActivity() {
    private val binding by lazy { ActivityTransactionBinding.inflate(layoutInflater) }
    private val spinnerData = listOf("시장가", "지정가")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val spinnerAdapter = ArrayAdapter<String>(
            this,
            R.layout.item_spinner_transaction_type,
            spinnerData
        )
        binding.spinnerTranscationType.adapter = spinnerAdapter

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
            "editBuy" -> {
                binding.buttonTranscation.text = "정정"
                binding.textBuyorSell.text = "정정합니다."
                //api 설정 추가
            }
            "editSell" -> {
                binding.buttonTranscation.text = "정정"
                binding.textBuyorSell.text = "정정합니다."
                //api 설정 추가
            }
        }

        binding.spinnerTranscationType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position){
                    0 -> {
                        binding.editPrice.inputType = InputType.TYPE_NULL
                        binding.editPrice.text = binding.nowPrice.text
                        binding.editPrice.setHintTextColor(rgb(46, 204, 113))
                        binding.editAmount.hint = "수량"
                    }
                    1 -> {
                        binding.editPrice.inputType = InputType.TYPE_CLASS_NUMBER
                        binding.editPrice.hint = "가격"
                        binding.editPrice.text = ""
                        binding.editPrice.setHintTextColor(rgb(190, 190, 190))
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.editPrice.inputType = InputType.TYPE_NULL
            }
        }

        binding.editPrice.text = binding.nowPrice.text
        binding.editAmount.hint = "수량"
    }
}