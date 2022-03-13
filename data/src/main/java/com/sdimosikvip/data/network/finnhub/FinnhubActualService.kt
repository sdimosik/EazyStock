package com.sdimosikvip.data.network.finnhub

import com.sdimosikvip.data.network.finnhub.models.CandlesByPeriodResponse
import com.sdimosikvip.data.network.finnhub.models.StockPriceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FinnhubActualService {

    @GET("quote")
    suspend fun getStockPrice(
        @Query("symbol") ticker: String
    ): Response<StockPriceResponse>

    @GET("stock/candle")
    suspend fun getCandlesByPeriod(
        @Query("symbol") ticker: String,
        @Query("resolution") resolution: String,
        @Query("from") from: Long,
        @Query("to") to: Long
    ): Response<CandlesByPeriodResponse>
}