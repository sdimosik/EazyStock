package com.sdimosikvip.domain.interactor

import com.sdimosikvip.domain.common.ResultResponse
import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.repository.StockRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetStockUseCase {
    suspend fun execute(ticker: String): Flow<ResultResponse<StockCompanyDomain>>
}

class GetStockUseCaseImpl @Inject constructor(
    private val stockRepository: StockRepository
) : GetStockUseCase {

    override suspend fun execute(ticker: String): Flow<ResultResponse<StockCompanyDomain>> =
        stockRepository.getStock(ticker)
}