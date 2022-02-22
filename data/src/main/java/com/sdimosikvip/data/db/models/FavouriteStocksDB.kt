package com.sdimosikvip.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sdimosikvip.data.db.models.FavouriteStocksDB.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class FavouriteStocksDB(
    @PrimaryKey
    val ticker: String
) {
    companion object {
        const val TABLE_NAME = "favorite_stock"
    }
}