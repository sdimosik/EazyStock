package com.sdimosikvip.eazystock.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.math.log

@Parcelize
data class StockCompanyUI(
    val country: String?,
    val currency: String,
    val exchange: String,
    val finnhubIndustry: String,
    val ipo: String,
    val logo: String,
    val marketCapitalization: Double,
    val name: String,
    val phone: String,
    val shareOutstanding: Double,
    val ticker: String,
    val weburl: String
) : Parcelable {
    companion object {
        val EMPTY = StockCompanyUI(
            country = "",
            currency = "",
            exchange = "",
            finnhubIndustry = "",
            ipo = "",
            logo = "",
            marketCapitalization = 0.0,
            name = "",
            phone = "",
            shareOutstanding = 0.0,
            ticker = "",
            weburl = ""
        )
    }
}
