package com.sdimosikvip.data.sources

import com.sdimosikvip.domain.common.ResultResponse
import com.sdimosikvip.domain.models.StockCompanyDomain

interface StockRemoteSource {

    suspend fun getCompanyStock(ticker: String): ResultResponse<StockCompanyDomain>
}