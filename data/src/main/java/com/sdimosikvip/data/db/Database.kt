package com.sdimosikvip.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sdimosikvip.data.db.favourite_tickers.FavouriteTickerDAO
import com.sdimosikvip.data.db.favourite_tickers.models.FavouriteTickerDB
import com.sdimosikvip.data.db.history_search.HistorySearchDAO
import com.sdimosikvip.data.db.history_search.models.HistoryTickerDB

@Database(
    entities = [FavouriteTickerDB::class, HistoryTickerDB::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun favouriteStockDAO(): FavouriteTickerDAO

    abstract fun historySearchDAO(): HistorySearchDAO
}