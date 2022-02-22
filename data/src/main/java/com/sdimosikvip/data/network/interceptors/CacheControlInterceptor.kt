package com.sdimosikvip.data.network.interceptors

import com.sdimosikvip.data.network.ConnectionManager
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CacheControlInterceptor @Inject constructor(
    private val connectionManager: ConnectionManager,
    private val ifConnectTimeLive: Int = 10,
    private val ifConnectTimeLiveUnit: TimeUnit = TimeUnit.SECONDS,
    private val ifNotConnectTimeLive: Int = 30,
    private val ifNotConnectTimeLiveUnit: TimeUnit = TimeUnit.DAYS,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())

        return if (connectionManager.isConnected()) {
            val cacheControl = CacheControl.Builder()
                .maxAge(ifConnectTimeLive, ifConnectTimeLiveUnit)
                .build()
            originalResponse.newBuilder()
                .header(
                    "Cache-Control",
                    cacheControl.toString()
                    //"public, max-age=" + ifConnectTimeLiveSec
                )
                .build()
        } else {
            val cacheControl = CacheControl.Builder()
                .onlyIfCached()
                .maxStale(ifNotConnectTimeLive, ifNotConnectTimeLiveUnit)
                .build()
            originalResponse.newBuilder()
                .header(
                    "Cache-Control",
                    cacheControl.toString()
                    //"public, only-if-cached, max-stale=" + ifNotConnectTimeLiveSec
                )
                .build();
        }
    }
}