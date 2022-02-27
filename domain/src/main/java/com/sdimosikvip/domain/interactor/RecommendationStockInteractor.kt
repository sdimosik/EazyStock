package com.sdimosikvip.domain.interactor

import com.sdimosikvip.domain.models.FavouriteTickerDomain
import com.sdimosikvip.domain.models.StockItemDomain
import com.sdimosikvip.domain.repository.StockRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface RecommendationStockInteractor {
    fun execute(): Flow<List<StockItemDomain>>
}

class RecommendationStockInteractorImpl @Inject constructor(
    private val stockRepository: StockRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RecommendationStockInteractor {

    override fun execute() = stockRepository.getStocks(
        listOf(
            FavouriteTickerDomain("FB"),
            FavouriteTickerDomain("AAPL"),
            FavouriteTickerDomain("AMZN"),
            FavouriteTickerDomain("NFLX"),
            FavouriteTickerDomain("GOOGL"),
            FavouriteTickerDomain("TSLA"),
            FavouriteTickerDomain("COKE"),
            FavouriteTickerDomain("EBAY"),
            FavouriteTickerDomain("KOD"),
        )
    )
}