package com.sdimosikvip.data.network.finnhub

import com.sdimosikvip.data.network.finnhub.models.StockCompanyResponse
import com.sdimosikvip.data.network.finnhub.models.SymbolLookupResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FinnhubCommonService {

    @GET("stock/profile2")
    suspend fun getStockCompany(
        @Query("symbol") ticker: String
    ): Response<StockCompanyResponse>

    @GET("search")
    suspend fun searchSymbol(
        @Query("q") text: String
    ): Response<SymbolLookupResponse>
}