package com.sdimosikvip.data.network.mboum.models


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("count")
    val count: Int,
    @SerializedName("jobTimestamp")
    val jobTimestamp: Long,
    @SerializedName("quotes")
    val quotes: List<String>,
    @SerializedName("startInterval")
    val startInterval: Long
)