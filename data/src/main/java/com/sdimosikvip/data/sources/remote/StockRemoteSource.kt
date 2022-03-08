package com.sdimosikvip.data.sources.remote

import com.sdimosikvip.domain.Outcome
import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.models.StockPriceDomain
import com.sdimosikvip.domain.models.SymbolLookupDomain
import com.sdimosikvip.domain.models.TickersDomain

interface StockRemoteSource {

    suspend fun getCompanyStock(ticker: String): Outcome<StockCompanyDomain>

    suspend fun getPriceStock(ticker: String): Outcome<StockPriceDomain>

    suspend fun getMostWatcherTickers(): Outcome<TickersDomain>

    suspend fun searchSymbol(text: String): Outcome<SymbolLookupDomain>

    suspend fun getNullableCompanyStock(ticker: String): Outcome<StockCompanyDomain>?

    suspend fun getNullablePriceStock(ticker: String): Outcome<StockPriceDomain>?

    suspend fun getNullableMostWatcherTickers(): Outcome<TickersDomain>?

    suspend fun searchNullableSymbol(text: String): Outcome<SymbolLookupDomain>?
}