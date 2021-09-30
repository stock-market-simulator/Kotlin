package com.kunize.stock_market_simulator.server.DTO

import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.kunize.stock_market_simulator.etcData.BookmarkFormat
import com.kunize.stock_market_simulator.etcData.interestFormat
import org.json.JSONArray
import java.lang.Exception
import kotlin.math.round
import kotlin.math.roundToInt

data class Bookmark(
    @SerializedName("bookmark")
    private val bookmark: JsonArray
) {
    fun getBookmarkByInterestFormat(): MutableList<interestFormat> {
        val list = mutableListOf<interestFormat>()
        val jsonArr = JSONArray(bookmark.toString())
        for(i in 0 until jsonArr.length()) {
            val data = jsonArr.getJSONObject(i)
            val name = data.getString("name")
            val currentPrice = data.getString("currentPrice")
            val prevPrice = data.getString("previousPrice")
            var rate: Float = try {
                (currentPrice.toFloat() - prevPrice.toFloat()) / prevPrice.toFloat()
            } catch (e: Exception) {
                0f
            }
            if(rate.isNaN())
                rate = 0f
            rate = ((rate * 10000).roundToInt() /100.0).toFloat()

            val tmp = interestFormat(name, currentPrice.toInt(), rate)
            list.add(tmp)
        }
        return list
    }

    //TODO 추후 삭제 가능성 높음
    fun getBookmarkInfo(): MutableList<BookmarkFormat> {
        val list = mutableListOf<BookmarkFormat>()
        val jsonArr = JSONArray(bookmark.toString())
        for(i in 0 until jsonArr.length()) {
            val data = jsonArr.getJSONObject(i)
            val name = data.getString("name")
            val currentPrice = data.getString("currentPrice")
            val prevPrice = data.getString("previousPrice")
            var rate: Float = try {
                (currentPrice.toFloat() - prevPrice.toFloat()) / prevPrice.toFloat()
            } catch (e: Exception) {
                0f
            }
            if(rate.isNaN())
                rate = 0f
            rate = ((rate * 100).roundToInt() /100.0).toFloat()

            val tmp = BookmarkFormat(name, currentPrice, prevPrice, rate)
            list.add(tmp)
        }
        return list
    }

    fun isBookmarked(name: String): Boolean {
        val list = getBookmarkInfo()
        val find = list.find { it.name == name }
        Log.d("bookmarkTest","$find")
        return find != null
    }
}