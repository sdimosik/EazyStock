package com.sdimosikvip.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sdimosikvip.data.db.models.FavouriteTickerDB

@Database(
    entities = [FavouriteTickerDB::class],
    version = 1,
    exportSchema = false
)
abstract class FavouriteTickerDatabase : RoomDatabase() {
    abstract fun favouriteStockDAO(): FavouriteTickerDAO
}