package com.sdimosikvip.data.network.finnhub.models


import com.google.gson.annotations.SerializedName

data class ResultResponse(
    @SerializedName("description")
    val description: String,
    @SerializedName("displaySymbol")
    val displaySymbol: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("type")
    val type: String?
)