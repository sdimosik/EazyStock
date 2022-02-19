package com.sdimosikvip.data.sources

import com.sdimosikvip.data.network.StockService
import com.sdimosikvip.data.network.models.StockCompanyResponse
import com.sdimosikvip.data.network.models.StockPriceResponse
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.models.StockPriceDomain
import javax.inject.Inject

class StockRemoteSourceImpl @Inject constructor(
    private val stockService: StockService,
    private val stockCompanyMapper: BaseMapper<StockCompanyResponse, StockCompanyDomain>,
    private val stockPriceMapper: BaseMapper<StockPriceResponse, StockPriceDomain>
) : StockRemoteSource, BaseRemoteSource() {

    override suspend fun getCompanyStock(ticker: String) =
        getResult(stockCompanyMapper) { stockService.getStockCompany(ticker) }

    override suspend fun getPriceStock(ticker: String) =
        getResult(stockPriceMapper) { stockService.getStockPrice(ticker) }
}