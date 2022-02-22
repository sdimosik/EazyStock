package com.sdimosikvip.domain.interactor

import com.sdimosikvip.domain.models.FavouriteStocksDomain
import com.sdimosikvip.domain.models.StockItemDomain
import com.sdimosikvip.domain.repository.StockRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FavouriteStockInteractor {
    suspend fun save(ticker: String)
    suspend fun delete(ticker: String)
    suspend fun getFavouriteTickers(): Flow<List<FavouriteStocksDomain>>
    suspend fun getFavouriteStocks(tickers: List<String>): Flow<List<StockItemDomain>>
}

class FavouriteStockInteractorImpl @Inject constructor(
    private val stockRepository: StockRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : FavouriteStockInteractor {

    override suspend fun save(ticker: String) = stockRepository.saveFavouriteStock(ticker)

    override suspend fun delete(ticker: String) = stockRepository.deleteFavouriteStock(ticker)

    override suspend fun getFavouriteTickers(): Flow<List<FavouriteStocksDomain>> =
        stockRepository.getFavouriteStocks()

    override suspend fun getFavouriteStocks(tickers: List<String>) =
        stockRepository.getStocks(tickers)

}