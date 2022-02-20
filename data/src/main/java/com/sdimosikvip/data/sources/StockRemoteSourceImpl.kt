package com.sdimosikvip.data.sources

import com.sdimosikvip.data.network.finnhub.FinnhubService
import com.sdimosikvip.data.network.finnhub.models.StockCompanyResponse
import com.sdimosikvip.data.network.finnhub.models.StockPriceResponse
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.models.StockPriceDomain
import javax.inject.Inject

class StockRemoteSourceImpl @Inject constructor(
    private val finnhubService: FinnhubService,
    private val stockCompanyMapper: BaseMapper<StockCompanyResponse, StockCompanyDomain>,
    private val stockPriceMapper: BaseMapper<StockPriceResponse, StockPriceDomain>
) : StockRemoteSource, BaseRemoteSource() {

    override suspend fun getCompanyStock(ticker: String) =
        getResult(stockCompanyMapper) { finnhubService.getStockCompany(ticker) }

    override suspend fun getPriceStock(ticker: String) =
        getResult(stockPriceMapper) { finnhubService.getStockPrice(ticker) }
}