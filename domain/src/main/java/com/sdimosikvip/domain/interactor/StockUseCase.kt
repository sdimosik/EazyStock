package com.sdimosikvip.domain.interactor

import com.sdimosikvip.domain.models.StockItemDomain
import com.sdimosikvip.domain.repository.StockRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface RecommendationStockInteractor {
    suspend fun execute(): Flow<MutableList<StockItemDomain>>
}

class RecommendationStockInteractorImpl @Inject constructor(
    private val stockRepository: StockRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RecommendationStockInteractor {

    override suspend fun execute() = flow {
        val list: MutableList<StockItemDomain> = mutableListOf()
        val FAANG_LIST = listOf("FB", "AAPL", "AMZN", "NFLX", "GOOGL")

        for (ticker in FAANG_LIST) {
            val companyStockDeferred = stockRepository.getCompanyStock(ticker)
            val priceStockDeferred = stockRepository.getPriceStock(ticker)
            list.add(StockItemDomain(companyStockDeferred, priceStockDeferred))
            emit(list)
        }
    }.flowOn(defaultDispatcher)
}