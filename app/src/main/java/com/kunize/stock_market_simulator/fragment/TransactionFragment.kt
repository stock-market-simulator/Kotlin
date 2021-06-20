package com.kunize.stock_market_simulator.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kunize.stock_market_simulator.R
import com.kunize.stock_market_simulator.SearchActivity
import com.kunize.stock_market_simulator.databinding.FragmentTransactionBinding

class TransactionFragment : Fragment() {
    lateinit var binding: FragmentTransactionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransactionBinding.inflate(inflater, container, false)
        binding.searchButton.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}