package com.sdimosikvip.data.sources

import com.sdimosikvip.data.network.StockService
import com.sdimosikvip.data.network.models.StockCompanyResponse
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.StockCompanyDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class StockRemoteSourceImpl @Inject constructor(
    private val stockService: StockService,
    private val stockCompanyMapper: BaseMapper<StockCompanyResponse, StockCompanyDomain>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : StockRemoteSource, BaseRemoteSource() {

    // TODO
    //  Разобраться с dispatchers

    override suspend fun getCompanyStock(ticker: String) =
        getResult(dispatcher, stockCompanyMapper) { stockService.getStockCompany(ticker) }
}