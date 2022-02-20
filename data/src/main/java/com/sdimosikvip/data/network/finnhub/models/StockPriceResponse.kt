package com.sdimosikvip.data.network.finnhub.models


import com.google.gson.annotations.SerializedName

data class StockPriceResponse(
    @SerializedName("c")
    val c: Double,
    @SerializedName("d")
    val d: Double,
    @SerializedName("dp")
    val dp: Double,
    @SerializedName("h")
    val h: Double,
    @SerializedName("l")
    val l: Double,
    @SerializedName("o")
    val o: Double,
    @SerializedName("pc")
    val pc: Double,
    @SerializedName("t")
    val t: Long
)