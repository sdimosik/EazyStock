package com.sdimosikvip.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sdimosikvip.data.db.cache.StockDAO
import com.sdimosikvip.data.db.cache.models.StockDB
import com.sdimosikvip.data.db.favourite.FavouriteTickerDAO
import com.sdimosikvip.data.db.favourite.models.FavouriteTickerDB

@Database(
    entities = [StockDB::class, FavouriteTickerDB::class],
    version = 1,
    exportSchema = false
)
abstract class StockDatabase : RoomDatabase() {
    abstract fun stockDAO(): StockDAO
    abstract fun favouriteTickerDAO(): FavouriteTickerDAO
}