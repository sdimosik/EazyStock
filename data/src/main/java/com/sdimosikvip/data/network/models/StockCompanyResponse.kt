package com.sdimosikvip.data.network.models


import com.google.gson.annotations.SerializedName

data class StockCompanyResponse(
    @SerializedName("country")
    val country: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("exchange")
    val exchange: String,
    @SerializedName("finnhubIndustry")
    val finnhubIndustry: String,
    @SerializedName("ipo")
    val ipo: String,
    @SerializedName("logo")
    val logo: String,
    @SerializedName("marketCapitalization")
    val marketCapitalization: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("shareOutstanding")
    val shareOutstanding: Double,
    @SerializedName("ticker")
    val ticker: String,
    @SerializedName("weburl")
    val weburl: String
)