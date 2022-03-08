package com.sdimosikvip.data.network.finnhub.models


import com.google.gson.annotations.SerializedName

data class SymbolLookupResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("result")
    val result: List<ResultResponse>
)