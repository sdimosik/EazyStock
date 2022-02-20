package com.sdimosikvip.data.network.mboum.models


import com.google.gson.annotations.SerializedName

data class MostWatchedTicketResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("meta")
    val meta: Meta
)
