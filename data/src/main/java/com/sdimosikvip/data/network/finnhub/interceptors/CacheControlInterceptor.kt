package com.sdimosikvip.data.network.finnhub.interceptors

import com.sdimosikvip.data.network.ConnectionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class CacheControlInterceptor @Inject constructor(
    private val connectionManager: ConnectionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())

        return if (connectionManager.isConnected()) {
            val maxAge = 60 // read from cache for 1 minute
            originalResponse.newBuilder()
                .header("Cache-Control", "public, max-age=" + maxAge)
                .build()
        } else {
            val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
            originalResponse.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                .build();
        }
    }

}