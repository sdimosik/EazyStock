package com.sdimosikvip.data.sources.remote

import com.sdimosikvip.data.network.ConnectionManager
import com.sdimosikvip.data.network.finnhub.FinnhubActualService
import com.sdimosikvip.data.network.finnhub.FinnhubCommonService
import com.sdimosikvip.data.network.finnhub.models.StockCompanyResponse
import com.sdimosikvip.data.network.finnhub.models.StockPriceResponse
import com.sdimosikvip.data.network.finnhub.models.SymbolLookupResponse
import com.sdimosikvip.data.network.mboum.MboumService
import com.sdimosikvip.data.network.mboum.models.MostWatchedTicketResponse
import com.sdimosikvip.domain.Outcome
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.models.StockPriceDomain
import com.sdimosikvip.domain.models.SymbolLookupDomain
import com.sdimosikvip.domain.models.TickersDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StockRemoteSourceImpl @Inject constructor(
    private val finnhubActualService: FinnhubActualService,
    private val finnhubCommonService: FinnhubCommonService,
    private val mboumService: MboumService,
    private val stockCompanyMapper: BaseMapper<StockCompanyResponse, StockCompanyDomain>,
    private val stockPriceMapper: BaseMapper<StockPriceResponse, StockPriceDomain>,
    private val tickerMapper: BaseMapper<MostWatchedTicketResponse, TickersDomain>,
    private val symbolLookupResponseMapper: BaseMapper<SymbolLookupResponse, SymbolLookupDomain>,
    private val connectionManager: ConnectionManager,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : StockRemoteSource, BaseRemoteSource() {

    override suspend fun getCompanyStock(ticker: String) = withContext(defaultDispatcher) {
        getResult(
            connectionManager,
            stockCompanyMapper
        ) { finnhubCommonService.getStockCompany(ticker) }
    }

    override suspend fun getPriceStock(ticker: String) = withContext(defaultDispatcher) {
        getResult(
            connectionManager,
            stockPriceMapper
        ) { finnhubActualService.getStockPrice(ticker) }
    }

    override suspend fun getMostWatcherTickers(): Outcome<TickersDomain> =
        withContext(defaultDispatcher) {
            getResult(connectionManager, tickerMapper) { mboumService.getMostWatchedTickers() }
        }

    override suspend fun searchSymbol(text: String): Outcome<SymbolLookupDomain> =
        withContext(defaultDispatcher) {
            getResult(
                connectionManager,
                symbolLookupResponseMapper
            ) { finnhubCommonService.searchSymbol(text) }
        }

    override suspend fun getNullableCompanyStock(ticker: String): Outcome<StockCompanyDomain>? =
        withContext(defaultDispatcher) {
            getNullableResult(
                connectionManager,
                stockCompanyMapper
            ) { finnhubCommonService.getStockCompany(ticker) }
        }

    override suspend fun getNullablePriceStock(ticker: String): Outcome<StockPriceDomain>? =
        withContext(defaultDispatcher) {
            getNullableResult(
                connectionManager,
                stockPriceMapper
            ) { finnhubActualService.getStockPrice(ticker) }
        }

    override suspend fun getNullableMostWatcherTickers(): Outcome<TickersDomain>? =
        withContext(defaultDispatcher) {
            getNullableResult(connectionManager, tickerMapper) { mboumService.getMostWatchedTickers() }
        }

    override suspend fun searchNullableSymbol(text: String): Outcome<SymbolLookupDomain>? =
        withContext(defaultDispatcher) {
            getNullableResult(
                connectionManager,
                symbolLookupResponseMapper
            ) { finnhubCommonService.searchSymbol(text) }
        }
}