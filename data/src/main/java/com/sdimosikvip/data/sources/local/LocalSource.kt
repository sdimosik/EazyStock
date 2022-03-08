package com.sdimosikvip.data.sources.local

import com.sdimosikvip.data.db.favourite_tickers.models.FavouriteTickerDB
import com.sdimosikvip.data.db.history_search.models.HistoryTickerDB
import kotlinx.coroutines.flow.Flow

interface LocalSource {

    fun getFavouriteStock(): Flow<List<FavouriteTickerDB>>

    suspend fun getOneFavouriteStock(ticker: String): FavouriteTickerDB?

    suspend fun insertFavouriteStock(favouriteTickerDB: FavouriteTickerDB)

    suspend fun deleteFavoriteStock(favouriteTickerDB: FavouriteTickerDB)

    suspend fun getFavouriteStockOneShot(): List<FavouriteTickerDB>

    fun getHistory(): Flow<List<HistoryTickerDB>>

    suspend fun addTickerWithAutoClean(historyTickerDB: HistoryTickerDB)
}