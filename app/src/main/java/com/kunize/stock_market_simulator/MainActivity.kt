package com.kunize.stock_market_simulator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.kunize.stock_market_simulator.adapter.CustomFragmentStateAdapter
import com.kunize.stock_market_simulator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}
    private val tabTextList = arrayListOf("홈","거래내역","내 정보","설정")
    private val tabLayoutIconArray = arrayOf(R.drawable.ic_home_24,R.drawable.ic_transcation_24,R.drawable.ic_myinfo_24,
    R.drawable.ic_settings_24)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.viewPager2.adapter = CustomFragmentStateAdapter(this)
        TabLayoutMediator(binding.tabLayout,binding.viewPager2) {
            tab, position ->
            tab.text = tabTextList[position]
            tab.setIcon(tabLayoutIconArray[position])
        }.attach()
    }
}