package com.sdimosikvip.data.repository

import com.sdimosikvip.data.sources.StockRemoteSource
import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.models.StockPriceDomain
import com.sdimosikvip.domain.models.TickersDomain
import com.sdimosikvip.domain.repository.StockRepository
import com.sdimosikvip.domain.requireValue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
    private val remoteDataSource: StockRemoteSource,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : StockRepository {

    override suspend fun getCompanyStock(
        ticker: String
    ): StockCompanyDomain =
        withContext(defaultDispatcher) { remoteDataSource.getCompanyStock(ticker).requireValue() }

    override suspend fun getPriceStock(
        ticker: String
    ): StockPriceDomain =
        withContext(defaultDispatcher) { remoteDataSource.getPriceStock(ticker).requireValue() }

    override suspend fun getMostWatchedTickers(): TickersDomain =
        withContext(defaultDispatcher) { remoteDataSource.getMostWatcherTickers().requireValue() }
}
