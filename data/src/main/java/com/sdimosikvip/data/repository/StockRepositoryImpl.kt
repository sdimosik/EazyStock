package com.sdimosikvip.data.repository

import com.sdimosikvip.data.sources.StockRemoteSource
import com.sdimosikvip.domain.Outcome
import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.repository.StockRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
    private val remoteDataSource: StockRemoteSource,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : StockRepository {

    override suspend fun getStock(
        ticker: String
    ): Flow<Outcome<StockCompanyDomain>> = flow {
        emit(remoteDataSource.getCompanyStock(ticker))
    }.flowOn(defaultDispatcher)
}
