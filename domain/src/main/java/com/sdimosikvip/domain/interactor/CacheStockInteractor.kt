package com.sdimosikvip.domain.interactor

import com.sdimosikvip.domain.models.StockItemDomain
import com.sdimosikvip.domain.repository.StockRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CacheStockInteractor {
    suspend fun saveStock(stockItemDomain: StockItemDomain)
    suspend fun deleteStock(stockItemDomain: StockItemDomain)
    fun getCacheStocksFlow(): Flow<List<StockItemDomain>>
    fun updateOrLoadStocksFlow(tickers: List<String> = listOf()): Flow<List<StockItemDomain>>
    suspend fun getCacheStocks(): List<StockItemDomain>
}

class CacheStockInteractorImpl @Inject constructor(
    private val stockRepository: StockRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CacheStockInteractor {

    override suspend fun saveStock(stockItemDomain: StockItemDomain) =
        stockRepository.saveStock(stockItemDomain)

    override suspend fun deleteStock(stockItemDomain: StockItemDomain) =
        stockRepository.deleteSaveStock(stockItemDomain)

    override fun getCacheStocksFlow(): Flow<List<StockItemDomain>> =
        stockRepository.getCacheStocksFlow()

    override fun updateOrLoadStocksFlow(tickers: List<String>) =
        stockRepository.updateOrLoadStocks(tickers)

    override suspend fun getCacheStocks(): List<StockItemDomain> = stockRepository.getCacheStocks()

}