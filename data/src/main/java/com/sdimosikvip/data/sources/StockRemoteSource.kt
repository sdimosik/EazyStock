package com.sdimosikvip.data.sources

import com.sdimosikvip.domain.Outcome
import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.models.StockPriceDomain

interface StockRemoteSource {

    suspend fun getCompanyStock(ticker: String): Outcome<StockCompanyDomain>

    suspend fun getPriceStock(ticker: String): Outcome<StockPriceDomain>
}