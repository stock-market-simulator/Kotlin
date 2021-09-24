package com.kunize.stock_market_simulator.server

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var instance : Retrofit? = null
    private val gson = GsonBuilder().setLenient().create()

    fun getInstance() : Retrofit {
        if(instance == null) {
            instance = Retrofit.Builder()
                .baseUrl("http://13.124.191.36:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return instance!!
    }
}