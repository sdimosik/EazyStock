package com.sdimosikvip.data.sources

import com.sdimosikvip.data.network.finnhub.FinnhubService
import com.sdimosikvip.data.network.finnhub.models.StockCompanyResponse
import com.sdimosikvip.data.network.finnhub.models.StockPriceResponse
import com.sdimosikvip.data.network.mboum.MboumService
import com.sdimosikvip.data.network.mboum.models.MostWatchedTicketResponse
import com.sdimosikvip.domain.Outcome
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.models.StockPriceDomain
import com.sdimosikvip.domain.models.TickersDomain
import javax.inject.Inject

class StockRemoteSourceImpl @Inject constructor(
    private val finnhubService: FinnhubService,
    private val mboumService: MboumService,
    private val stockCompanyMapper: BaseMapper<StockCompanyResponse, StockCompanyDomain>,
    private val stockPriceMapper: BaseMapper<StockPriceResponse, StockPriceDomain>,
    private val tickerMapper: BaseMapper<MostWatchedTicketResponse, TickersDomain>
) : StockRemoteSource, BaseRemoteSource() {

    override suspend fun getCompanyStock(ticker: String) =
        getResult(stockCompanyMapper) { finnhubService.getStockCompany(ticker) }

    override suspend fun getPriceStock(ticker: String) =
        getResult(stockPriceMapper) { finnhubService.getStockPrice(ticker) }

    override suspend fun getMostWatcherTickers(): Outcome<TickersDomain> =
        getResult(tickerMapper) { mboumService.getMostWatchedTickers() }
}