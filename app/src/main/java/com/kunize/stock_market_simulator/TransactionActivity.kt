package com.kunize.stock_market_simulator

import android.graphics.Color.rgb
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.kunize.stock_market_simulator.databinding.ActivityTransactionBinding
import java.text.DecimalFormat


class TransactionActivity : AppCompatActivity() {
    private val binding by lazy { ActivityTransactionBinding.inflate(layoutInflater) }
    private val spinnerData = listOf("시장가", "지정가")
    private var editAmount: Long = 0
    private var editPrice: Long = 0
    private var total: Long = 0

    companion object {
        val decimalFormat = DecimalFormat("###,###")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.nowPrice.text = decimalFormat.format(90000)

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
            else -> {
                binding.buttonTranscation.text = "정정"
                binding.textBuyorSell.text = "정정합니다."
                val amount = intent.getStringExtra("amount")
                binding.editAmount.text = amount
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

        updateTotalPrice()

        binding.editAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //
            }

            override fun afterTextChanged(s: Editable?) {
                updateTotalPrice()
            }
        })

        binding.editPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //
            }

            override fun afterTextChanged(s: Editable?) {
                updateTotalPrice()
            }
        })

        binding.plusAmount.setOnClickListener {
            updateTotalPrice(1)
        }

        binding.minusAmount.setOnClickListener {
            if(editAmount > 0)
                updateTotalPrice(-1)
        }

    }

    private fun updateTotalPrice(plusMinus: Long = 0) {
        editAmount = if (binding.editAmount.text.toString().toLongOrNull() != null) {
            binding.editAmount.text.toString().toLong() + plusMinus
        } else 0 + plusMinus

        editPrice = if (binding.editPrice.text.toString() != "") {
            binding.editPrice.text.toString().replace(",","").toLong()
        } else 0

        total = editAmount * editPrice
        runOnUiThread {
            if(plusMinus != 0L) {
                when (editAmount) {
                    0L -> {
                        binding.editAmount.text = ""
                    }
                    else -> binding.editAmount.text = "$editAmount"
                }
            }
            binding.textTotalPrice.text = "총 "+ decimalFormat.format(total) + " KRW"
        }

    }
}