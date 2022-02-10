package com.sdimosikvip.domain.repository

import com.sdimosikvip.domain.common.ResultResponse
import com.sdimosikvip.domain.models.StockCompanyDomain
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getStock(ticker: String): Flow<ResultResponse<StockCompanyDomain>>
}