package com.sdimosikvip.data.sources.remote

import com.sdimosikvip.data.network.ConnectionManager
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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StockRemoteSourceImpl @Inject constructor(
    private val finnhubService: FinnhubService,
    private val mboumService: MboumService,
    private val stockCompanyMapper: BaseMapper<StockCompanyResponse, StockCompanyDomain>,
    private val stockPriceMapper: BaseMapper<StockPriceResponse, StockPriceDomain>,
    private val tickerMapper: BaseMapper<MostWatchedTicketResponse, TickersDomain>,
    private val connectionManager: ConnectionManager,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : StockRemoteSource, BaseRemoteSource() {

    override suspend fun getCompanyStock(ticker: String) = withContext(defaultDispatcher) {
        getResult(
            connectionManager,
            stockCompanyMapper
        ) { finnhubService.getStockCompany(ticker) }
    }

    override suspend fun getPriceStock(ticker: String) = withContext(defaultDispatcher) {
        getResult(connectionManager, stockPriceMapper) { finnhubService.getStockPrice(ticker) }
    }

    override suspend fun getMostWatcherTickers(): Outcome<TickersDomain> =
        withContext(defaultDispatcher) {
            getResult(connectionManager, tickerMapper) { mboumService.getMostWatchedTickers() }
        }
}