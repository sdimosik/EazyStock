package com.sdimosikvip.domain.repository

import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.models.StockPriceDomain
import com.sdimosikvip.domain.models.TickersDomain

interface StockRepository {

    suspend fun getCompanyStock(ticker: String): StockCompanyDomain

    suspend fun getPriceStock(ticker: String): StockPriceDomain

    suspend fun getMostWatchedTickers(): TickersDomain
}