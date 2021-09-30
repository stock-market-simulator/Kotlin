package com.kunize.stock_market_simulator.server

import com.google.gson.JsonObject
import com.kunize.stock_market_simulator.server.DTO.Bookmark
import com.kunize.stock_market_simulator.server.DTO.KospiKosdaq
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @GET("major")
    fun requestKospiKosdaq(): Call<KospiKosdaq>

    @FormUrlEncoded
    @POST("user")
    fun postUserId(
        @Field("token") token: String
    ): Call<JsonObject>

    @FormUrlEncoded
    @POST("bookmark")
    fun editBookmark(
        @Field("token") token: String,
        @Field("name") name:String
    ): Call<JsonObject>

    @GET("bookmark/{token}")
    fun getBookmark(
        @Path("token") token: String
    ): Call<Bookmark>
}