package com.kunize.stock_market_simulator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.google.gson.JsonObject
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import com.kunize.stock_market_simulator.databinding.ActivityStartBinding
import com.kunize.stock_market_simulator.server.ApiInterface
import com.kunize.stock_market_simulator.server.DTO.ErrorBody
import com.kunize.stock_market_simulator.server.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class StartActivity : AppCompatActivity() {

    private val binding by lazy { ActivityStartBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        KakaoSdk.init(this, "f288834ef1765b8f5c6715609da7199b")

        intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.d("KaKaoTEST", "로그인 실패", error)
            } else if (token != null) {
                Log.d("KaKaoTEST", "로그인 성공 ${token.accessToken}")
                userInfo()
                postUserId()
                binding.loginBtn.visibility = View.INVISIBLE
                //TODO 로그인 성공 후 모든 종목 리스트 받아오고 sharedPreferences에 저장해야함 json으로 저장해야 array 저장가능
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
                    postUserId()
                }
            }
        } else {
            //로그인 필요
            binding.loginBtn.visibility = View.VISIBLE
        }

        binding.loginBtn.setOnClickListener {
            kakaoLogin(callback)
        }
    }

    private fun postUserId() {
        val userIDPreferences = getSharedPreferences("userID", MODE_PRIVATE)
        val testUserID = userIDPreferences.getString("userID", "")
        if (testUserID != "") {
            Log.d("TEST", testUserID!!)
        }

        val retrofit = RetrofitClient.getInstance()
        val api = retrofit.create(ApiInterface::class.java)
        api.postUserId(testUserID).enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d("loginTest","실패 !${t.message}")
            }

            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                val result = response.body()
                val data = try {
                    JSONObject(result.toString())
                } catch (e: Exception) {
                    retrofit.responseBodyConverter<String>(
                        String::class.java,
                        String::class.java.annotations
                    ).convert(response.errorBody())
                }
                if(response.isSuccessful || data == "이미 존재하는 토큰입니다.")
                    startActivity(intent)

                Log.d("loginTest","$data")
            }
        })
    }


    private fun kakaoLogin(callback: (OAuthToken?, Throwable?) -> Unit) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

    private fun userInfo() {
        val userIDPreferences = getSharedPreferences("userID", Context.MODE_PRIVATE)

        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("KaKaoTEST", "사용자 정보 요청 실패", error)
            } else if (user != null) {

                userIDPreferences.edit {
                    putString("userID", "${user.id}")
                }
            }
        }
        val testUserID = userIDPreferences.getString("userID","")
        if (testUserID != null) {
            Log.d("TEST123",testUserID)
        }
    }

}