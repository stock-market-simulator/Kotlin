package com.kunize.stock_market_simulator.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kakao.sdk.user.UserApiClient
import com.kunize.stock_market_simulator.MainActivity
import com.kunize.stock_market_simulator.R
import com.kunize.stock_market_simulator.StartActivity
import com.kunize.stock_market_simulator.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    lateinit var binding: FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        val intent = Intent(activity, StartActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        binding.logoutBtn.setOnClickListener {
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Log.e("TEST", "연결 끊기 실패", error)
                } else {
                    Log.i("TEST", "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                    startActivity(intent)
                }
            }
        }
        return binding.root
    }
}