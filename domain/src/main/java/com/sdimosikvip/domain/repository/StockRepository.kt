package com.sdimosikvip.domain.repository

import com.sdimosikvip.domain.models.FavouriteTickerDomain
import com.sdimosikvip.domain.models.StockItemDomain
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    fun updateOrLoadStocks(
        tickers: List<String>
    ): Flow<MutableList<StockItemDomain>>

    suspend fun saveStock(stockItemDomain: StockItemDomain)

    suspend fun deleteSaveStock(stockItemDomain: StockItemDomain)

    suspend fun getCacheStocks(): List<StockItemDomain>

    fun getCacheStocksFlow(): Flow<List<StockItemDomain>>

    fun getCacheStocksFlow(listTicker: List<String>): Flow<List<StockItemDomain>>

    fun getFavouriteStocksFlow(): Flow<List<StockItemDomain>>

    fun getFavouriteTickersFlow(): Flow<List<FavouriteTickerDomain>>

    suspend fun saveFavouriteTickers(favouriteTickerDomain: FavouriteTickerDomain)

    suspend fun deleteFavouriteTickers(favouriteTickerDomain: FavouriteTickerDomain)
}