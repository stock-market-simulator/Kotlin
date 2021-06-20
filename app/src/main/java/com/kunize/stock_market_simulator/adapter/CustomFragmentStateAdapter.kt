package com.kunize.stock_market_simulator.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kunize.stock_market_simulator.fragment.HomeFragment
import com.kunize.stock_market_simulator.fragment.MyInfoFragment
import com.kunize.stock_market_simulator.fragment.SettingFragment
import com.kunize.stock_market_simulator.fragment.TransactionHistoryFragment

class CustomFragmentStateAdapter(fragmentActivity: FragmentActivity):
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> HomeFragment()
            1 -> TransactionHistoryFragment()
            2 -> MyInfoFragment()
            else -> SettingFragment()
        }
    }
}