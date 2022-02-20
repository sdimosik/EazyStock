package com.sdimosikvip.domain.repository

import com.sdimosikvip.domain.models.FavouriteStocksDomain
import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.models.StockPriceDomain
import com.sdimosikvip.domain.models.TickersDomain
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyStock(ticker: String): StockCompanyDomain

    suspend fun getPriceStock(ticker: String): StockPriceDomain

    suspend fun getMostWatchedTickers(): TickersDomain

    suspend fun saveFavouriteStock(ticker: String)

    suspend fun deleteFavouriteStock(ticker: String)

    suspend fun getFavouriteStocks(): Flow<List<FavouriteStocksDomain>>
}