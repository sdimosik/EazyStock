package com.sdimosikvip.data.db

import androidx.room.*
import com.sdimosikvip.data.db.models.FavouriteStocksDB
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteStockDAO {

    @Query("SELECT * FROM ${FavouriteStocksDB.TABLE_NAME}")
    fun getFavouriteStock(): Flow<List<FavouriteStocksDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteStock(favouriteStocksDB: FavouriteStocksDB)

    @Delete
    suspend fun deleteFavoriteStock(favouriteStocksDB: FavouriteStocksDB)
}