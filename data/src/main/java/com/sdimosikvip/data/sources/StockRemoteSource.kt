package com.sdimosikvip.data.sources

import com.sdimosikvip.domain.Outcome
import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.models.StockPriceDomain
import com.sdimosikvip.domain.models.TickersDomain

interface StockRemoteSource {

    suspend fun getCompanyStock(ticker: String): Outcome<StockCompanyDomain>

    suspend fun getPriceStock(ticker: String): Outcome<StockPriceDomain>

    suspend fun getMostWatcherTickers(): Outcome<TickersDomain>
}