package com.sdimosikvip.data.network

import com.sdimosikvip.data.network.models.StockCompanyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StockService {

    @GET("stock/profile2")
    suspend fun getStockCompany(
        @Query("symbol") ticker: String
    ): Response<StockCompanyResponse>
}