package com.kunize.stock_market_simulator.etcData

data class BookmarkFormat (
    val name: String,
    val currentPrice: String,
    val previousPrice: String,
    val rate: Float = 0f
)