package com.sdimosikvip.domain.repository

import com.sdimosikvip.domain.models.*
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    fun getStocks(
        favouriteTickerDomainList: List<FavouriteTickerDomain>
    ): Flow<MutableList<StockItemDomain>>

    suspend fun saveFavouriteStock(favouriteTickerDomain: FavouriteTickerDomain)

    suspend fun deleteFavouriteStock(favouriteTickerDomain: FavouriteTickerDomain)

    fun getFavouriteStocks(): Flow<List<FavouriteTickerDomain>>

    suspend fun getFavouriteStocksOneshot(): List<FavouriteTickerDomain>

    fun searchTicker(text: String): Flow<MutableList<StockItemDomain>>

    fun getHistorySearch(): Flow<List<TickerDomain>>

    suspend fun saveHistoryTickers(tickerDomain: TickerDomain)
}