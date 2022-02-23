package com.sdimosikvip.data.db.favourite

import androidx.room.*
import com.sdimosikvip.data.db.favourite.models.FavouriteTickerDB
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteTickerDAO {

    @Query("SELECT * FROM ${FavouriteTickerDB.TABLE_NAME}")
    fun getFavouriteTickersFlow(): Flow<List<FavouriteTickerDB>>

    @Query("SELECT * FROM ${FavouriteTickerDB.TABLE_NAME}")
    suspend fun getFavouriteTickers(): List<FavouriteTickerDB>

    @Query("SELECT * FROM ${FavouriteTickerDB.TABLE_NAME} WHERE tickerId = :ticker")
    suspend fun getOneFavouriteTicker(ticker: String): FavouriteTickerDB?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteTicker(favouriteTickerDB: FavouriteTickerDB)

    @Delete
    suspend fun deleteFavouriteTicker(favouriteTickerDB: FavouriteTickerDB)
}