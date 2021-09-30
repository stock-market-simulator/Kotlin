package com.kunize.stock_market_simulator.server.DTO

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName
import org.json.JSONArray

data class KospiKosdaq (
    @SerializedName("kospi")
    private val kospi: JsonArray,

    @SerializedName("kosdaq")
    private val kosdaq: JsonArray,
) {
    fun getKospi(): MutableList<Double> {
        val list = mutableListOf<Double>()
        val kospiJSON = JSONArray(kospi.toString())
        for(i in 0 until kospiJSON.length()) {
            val data = kospiJSON.getJSONObject(i)
            list.add(data.getDouble("Price"))
        }
        return list
    }

    fun getKosdaq(): MutableList<Double> {
        val list = mutableListOf<Double>()
        val kosdaqJSON = JSONArray(kosdaq.toString())
        for(i in 0 until kosdaqJSON.length()) {
            val data = kosdaqJSON.getJSONObject(i)
            list.add(data.getDouble("Price"))
        }
        return list
    }
}