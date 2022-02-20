package com.sdimosikvip.domain.interactor

import com.sdimosikvip.domain.models.FavouriteStocksDomain
import com.sdimosikvip.domain.repository.StockRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FavouriteStockInteractor {
    suspend fun save(ticker: String)
    suspend fun delete(ticker: String)
    suspend fun get(): Flow<List<FavouriteStocksDomain>>
}

class FavouriteStockInteractorImpl @Inject constructor(
    private val stockRepository: StockRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : FavouriteStockInteractor {

    override suspend fun save(ticker: String) = stockRepository.saveFavouriteStock(ticker)

    override suspend fun delete(ticker: String) = stockRepository.deleteFavouriteStock(ticker)

    override suspend fun get(): Flow<List<FavouriteStocksDomain>> =
        stockRepository.getFavouriteStocks()

}