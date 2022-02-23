package com.sdimosikvip.domain.interactor

import com.sdimosikvip.domain.models.StockItemDomain
import com.sdimosikvip.domain.repository.StockRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface RecommendationStockInteractor {
    suspend fun getRecommendationStock(): Flow<List<StockItemDomain>>
}

class RecommendationStockInteractorImpl @Inject constructor(
    private val stockRepository: StockRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RecommendationStockInteractor {

    // TODO api does not provide recommended
    override suspend fun getRecommendationStock() =
        stockRepository.updateOrLoadStocks (
            listOf(
                "FB",
                "AAPL",
                "AMZN",
                "NFLX",
                "GOOGL"
            )
        )
}