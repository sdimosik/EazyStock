package com.sdimosikvip.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sdimosikvip.data.db.models.FavouriteStocksDB

@Database(
    entities = [FavouriteStocksDB::class],
    version = 1,
    exportSchema = false
)
abstract class FavouriteStockDatabase : RoomDatabase() {
    abstract fun favouriteStockDAO(): FavouriteStockDAO
}