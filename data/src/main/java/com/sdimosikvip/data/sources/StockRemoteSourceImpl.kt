package com.sdimosikvip.data.sources

import com.sdimosikvip.data.network.StockService
import com.sdimosikvip.data.network.models.StockCompanyResponse
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.StockCompanyDomain
import javax.inject.Inject

class StockRemoteSourceImpl @Inject constructor(
    private val stockService: StockService,
    private val stockCompanyMapper: BaseMapper<StockCompanyResponse, StockCompanyDomain>,
) : StockRemoteSource, BaseRemoteSource() {

    override suspend fun getCompanyStock(ticker: String) =
        getResult(stockCompanyMapper) { stockService.getStockCompany(ticker) }
}