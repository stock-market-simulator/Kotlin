package com.kunize.stock_market_simulator.etcData

data class StockInfoFormat (
    var name: String,
    var price : Int,
    var amount: Int,
    var total: Int,
    var rate: Float = 0f
)