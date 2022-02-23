package com.sdimosikvip.data.db

import androidx.room.*
import com.sdimosikvip.data.db.models.FavouriteTickerDB
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteTickerDAO {

    @Query("SELECT * FROM ${FavouriteTickerDB.TABLE_NAME}")
    fun getFavouriteStock(): Flow<List<FavouriteTickerDB>>

    @Query("SELECT * FROM ${FavouriteTickerDB.TABLE_NAME} WHERE ticker = :ticker")
    suspend fun getOneFavouriteStock(ticker: String): FavouriteTickerDB?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteStock(favouriteTickerDB: FavouriteTickerDB)

    @Delete
    suspend fun deleteFavoriteStock(favouriteTickerDB: FavouriteTickerDB)
}