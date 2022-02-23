package com.sdimosikvip.data.db.favourite.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = FavouriteTickerDB.TABLE_NAME)
data class FavouriteTickerDB(
    @PrimaryKey
    val tickerId: String,
) {
    companion object {
        const val TABLE_NAME = "favourite_tickers"
    }
}
