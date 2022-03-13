package com.sdimosikvip.eazystock.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StockPriceUI(
    val currentPrice: Double,
    val change: Double,
    val percentChange: Double,
    val highPriceOfDay: Double,
    val lowPriceOfDay: Double,
    val openPriceOfDay: Double,
    val previousClosePrice: Double,
    val timestampResponse: Long
) : Parcelable {
    companion object {
        val EMPTY = StockPriceUI(
            currentPrice = 0.0,
            change = 0.0,
            percentChange = 0.0,
            highPriceOfDay = 0.0,
            lowPriceOfDay = 0.0,
            openPriceOfDay = 0.0,
            previousClosePrice = 0.0,
            timestampResponse = 0,
        )
    }
}
