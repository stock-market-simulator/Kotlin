package com.kunize.stock_market_simulator

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class CustomFragmentStateAdapter(fragmentActivity: FragmentActivity):
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> HomeFragment()
            1 -> TransactionFragment()
            2 -> MyInfoFragment()
            else -> SettingFragment()
        }
    }
}