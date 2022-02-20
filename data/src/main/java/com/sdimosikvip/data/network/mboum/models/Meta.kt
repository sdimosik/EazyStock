package com.sdimosikvip.data.network.mboum.models


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("data_status")
    val dataStatus: String
)