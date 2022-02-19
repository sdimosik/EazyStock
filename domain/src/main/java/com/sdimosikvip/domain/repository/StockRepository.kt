package com.sdimosikvip.domain.repository

import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.models.StockPriceDomain

interface StockRepository {

    suspend fun getCompanyStock(ticker: String): StockCompanyDomain

    suspend fun getPriceStock(ticker: String): StockPriceDomain
}