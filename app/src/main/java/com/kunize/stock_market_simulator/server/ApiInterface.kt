package com.kunize.stock_market_simulator.server

import com.kunize.stock_market_simulator.server.DTO.KospiKosdaq
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("major")
    fun requestKospiKosdaq(): Call<KospiKosdaq>
}