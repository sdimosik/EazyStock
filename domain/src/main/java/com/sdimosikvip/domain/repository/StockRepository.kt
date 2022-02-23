package com.sdimosikvip.domain.repository

import com.sdimosikvip.domain.models.FavouriteStocksDomain
import com.sdimosikvip.domain.models.StockItemDomain
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getStocks(
        tickers: List<String>
    ): Flow<MutableList<StockItemDomain>>

    suspend fun saveFavouriteStock(ticker: String)

    suspend fun deleteFavouriteStock(ticker: String)

    suspend fun getFavouriteStocks(): Flow<List<FavouriteStocksDomain>>
}