package com.sdimosikvip.data.db.cache.models

import androidx.room.Entity

@Entity(tableName = StockDB.TABLE_NAME)
data class CompanyInfoDB(
    val country: String,
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
)
