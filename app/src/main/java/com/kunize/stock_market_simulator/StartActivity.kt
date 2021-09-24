package com.kunize.stock_market_simulator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import com.kunize.stock_market_simulator.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {

    private val binding by lazy { ActivityStartBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        KakaoSdk.init(this, "f288834ef1765b8f5c6715609da7199b")

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.d("KaKaoTEST", "로그인 실패", error)
            } else if (token != null) {
                Log.d("KaKaoTEST", "로그인 성공 ${token.accessToken}")
                userInfo()
                binding.loginBtn.visibility = View.INVISIBLE
                //TODO 로그인 성공 후 모든 종목 리스트 받아오고 sharedPreferences에 저장해야함 json으로 저장해야 array 저장가능
                startActivity(intent)
            }
        }

        binding.loginBtn.visibility = View.INVISIBLE

        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error != null) {
                    if (error is KakaoSdkError && error.isInvalidTokenError()) {
                        //로그인 필요
                        binding.loginBtn.visibility = View.VISIBLE
                    } else {
                        //기타 에러
                    }
                } else {
                    Log.d("TEST", "토큰이 존재함")
                    userInfo()
                    startActivity(intent)
                }
            }
        } else {
            //로그인 필요
            binding.loginBtn.visibility = View.VISIBLE
        }

        binding.loginBtn.setOnClickListener {
            kakaoLogin(callback)
        }

        //TODO TEST 코드 릴리즈 배포 시 삭제해야함
        binding.tempLogo.setOnClickListener {
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Log.e("TEST", "연결 끊기 실패", error)
                } else {
                    Log.i("TEST", "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                }
            }
        }

    }


    private fun kakaoLogin(callback: (OAuthToken?, Throwable?) -> Unit) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

    private fun userInfo() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("KaKaoTEST", "사용자 정보 요청 실패", error)
            } else if (user != null) {
                Log.d(
                    "KaKaoTEST", "사용자 정보 요청 성공" +
                            "\n회원번호: ${user.id}" +
                            "\n이메일: ${user.kakaoAccount?.email}" +
                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                            "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}" +
                            "\n ${user.toString()}"
                )
                val userIDPreferences = getSharedPreferences("userID", Context.MODE_PRIVATE)
                userIDPreferences.edit{
                    putString("userID","${user.id}")
                }
                val testUserID = userIDPreferences.getString("userID","userID 저장되지 않음")
                if (testUserID != null) {
                    Log.d("TEST",testUserID)
                }
            }
        }
        // 토큰 정보 보기
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Log.d("KaKaoTEST", "토큰 정보 보기 실패", error)
            } else if (tokenInfo != null) {
                Toast.makeText(this, "${tokenInfo.id}",Toast.LENGTH_LONG).show()
                Log.d(
                    "KaKaoTEST", "토큰 정보 보기 성공" +
                            "\n회원번호: ${tokenInfo.id}" +
                            "\n만료시간: ${tokenInfo.expiresIn} 초"
                )
            }
        }
    }
}