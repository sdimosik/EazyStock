package com.sdimosikvip.data.network.mboum

import com.sdimosikvip.data.network.mboum.models.MostWatchedTicketResponse
import retrofit2.Response
import retrofit2.http.GET


interface MboumService {

    @GET("tr/trending")
    suspend fun getMostWatchedTickers(): Response<MostWatchedTicketResponse>
}