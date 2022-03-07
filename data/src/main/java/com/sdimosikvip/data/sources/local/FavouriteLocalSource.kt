package com.sdimosikvip.data.sources.local

import com.sdimosikvip.data.db.models.FavouriteTickerDB
import kotlinx.coroutines.flow.Flow

interface FavouriteLocalSource {

    fun getFavouriteStock(): Flow<List<FavouriteTickerDB>>

    suspend fun getOneFavouriteStock(ticker: String): FavouriteTickerDB?

    suspend fun insertFavouriteStock(favouriteTickerDB: FavouriteTickerDB)

    suspend fun deleteFavoriteStock(favouriteTickerDB: FavouriteTickerDB)

    suspend fun getFavouriteStockOneShot(): List<FavouriteTickerDB>
}