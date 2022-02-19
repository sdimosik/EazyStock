package com.sdimosikvip.data.sources

import com.sdimosikvip.domain.Outcome
import com.sdimosikvip.domain.models.StockCompanyDomain

interface StockRemoteSource {

    suspend fun getCompanyStock(ticker: String): Outcome<StockCompanyDomain>
}