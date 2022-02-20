package com.sdimosikvip.data.network.finnhub

import com.sdimosikvip.data.network.finnhub.models.StockCompanyResponse
import com.sdimosikvip.data.network.finnhub.models.StockPriceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FinnhubService {

    @GET("stock/profile2")
    suspend fun getStockCompany(
        @Query("symbol") ticker: String
    ): Response<StockCompanyResponse>

    @GET("quote")
    suspend fun getStockPrice(
        @Query("symbol") ticker: String
    ): Response<StockPriceResponse>
}