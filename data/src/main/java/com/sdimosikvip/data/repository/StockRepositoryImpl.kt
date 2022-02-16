package com.sdimosikvip.data.repository

import com.sdimosikvip.data.sources.StockRemoteSource
import com.sdimosikvip.domain.common.Outcome
import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.repository.StockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StockRepositoryImpl(
    private val remoteDataSource: StockRemoteSource
) : StockRepository {

    override suspend fun getStock(
        ticker: String
    ): Flow<Outcome<StockCompanyDomain>> = flow {
        emit(Outcome.loading())
        emit(remoteDataSource.getCompanyStock(ticker))
    }
}
