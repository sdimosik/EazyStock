package com.sdimosikvip.data.network.interceptors

import com.sdimosikvip.data.network.ConnectionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class CacheControlInterceptor @Inject constructor(
    private val connectionManager: ConnectionManager,
    val ifConnectTimeLiveSec: Long = 60 * 60 * 24,
    val ifNotConnectTimeLiveSec: Long = 60 * 60 * 24 * 28,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())

        return if (connectionManager.isConnected()) {
            originalResponse.newBuilder()
                .header("Cache-Control", "public, max-age=" + ifConnectTimeLiveSec)
                .build()
        } else {
            originalResponse.newBuilder()
                .header(
                    "Cache-Control",
                    "public, only-if-cached, max-stale=" + ifNotConnectTimeLiveSec
                )
                .build();
        }
    }

}