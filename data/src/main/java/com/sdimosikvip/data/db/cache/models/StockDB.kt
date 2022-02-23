package com.sdimosikvip.data.db.cache.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sdimosikvip.data.db.cache.models.StockDB.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class StockDB(
    @PrimaryKey
    val tickerId: String,
    val timestamp: Long,
    val isFavourite: Boolean,
    @Embedded val companyInfo: CompanyInfoDB,
    @Embedded val priceInfo: PriceInfoDB,
) {
    companion object {
        const val TABLE_NAME = "stocks"
    }
}