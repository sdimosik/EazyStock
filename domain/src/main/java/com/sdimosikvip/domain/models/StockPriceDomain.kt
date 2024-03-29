package com.sdimosikvip.domain.models

data class StockPriceDomain(
    val currentPrice: Double,
    val change: Double,
    val percentChange: Double,
    val highPriceOfDay: Double,
    val lowPriceOfDay: Double,
    val openPriceOfDay: Double,
    val previousClosePrice: Double,
    val timestampResponse: Long
)
