package com.kunize.stock_market_simulator.server

import com.google.gson.JsonObject
import com.kunize.stock_market_simulator.server.DTO.KospiKosdaq
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    @GET("major")
    fun requestKospiKosdaq(): Call<KospiKosdaq>

    @FormUrlEncoded
    @POST("user")
    fun postUserId(
        @Field("token") token: String
    ): Call<JsonObject>
}