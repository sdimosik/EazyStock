package com.sdimosikvip.domain.interactor

import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.models.StockPriceDomain
import com.sdimosikvip.domain.repository.StockRepository
import javax.inject.Inject

interface RecommendationStockInteractor {
    suspend fun getCompanyInfo(ticker: String): StockCompanyDomain
    suspend fun getPriceInfo(ticker: String): StockPriceDomain
}

class RecommendationStockInteractorImpl @Inject constructor(
    private val stockRepository: StockRepository
) : RecommendationStockInteractor {

    override suspend fun getCompanyInfo(ticker: String): StockCompanyDomain =
        stockRepository.getCompanyStock(ticker)

    override suspend fun getPriceInfo(ticker: String): StockPriceDomain =
        stockRepository.getPriceStock(ticker)
}