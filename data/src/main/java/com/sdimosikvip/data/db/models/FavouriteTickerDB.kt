package com.sdimosikvip.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sdimosikvip.data.db.models.FavouriteTickerDB.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class FavouriteTickerDB(
    @PrimaryKey
    val ticker: String
) {
    companion object {
        const val TABLE_NAME = "favorite_stock"
    }
}