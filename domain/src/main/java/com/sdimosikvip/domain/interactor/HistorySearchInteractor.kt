package com.sdimosikvip.domain.interactor

import com.sdimosikvip.domain.models.StockItemDomain
import com.sdimosikvip.domain.models.TickerDomain
import com.sdimosikvip.domain.repository.StockRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface HistorySearchInteractor {
    fun search(text: String): Flow<List<StockItemDomain>>

    fun history(): Flow<List<TickerDomain>>

    suspend fun saveQueryHistory(tickerDomain: TickerDomain)
}

class HistorySearchInteractorImpl @Inject constructor(
    private val stockRepository: StockRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : HistorySearchInteractor {

    override fun search(text: String) = stockRepository.searchTicker(text)

    override fun history(): Flow<List<TickerDomain>> = stockRepository.getHistorySearch()

    override suspend fun saveQueryHistory(tickerDomain: TickerDomain) =
        stockRepository.saveHistoryTickers(tickerDomain)
}