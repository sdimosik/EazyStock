package com.sdimosikvip.data.network.interceptors

import com.sdimosikvip.data.network.ConnectionManager
import com.sdimosikvip.data.network.interceptors.Const.HEADER_CACHE_CONTROL
import com.sdimosikvip.data.network.interceptors.Const.HEADER_PRAGMA
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class CacheOfflineControlInterceptor @Inject constructor(
    private val connectionManager: ConnectionManager,
    private val ifNotConnectTimeLive: Int = 30,
    private val ifNotConnectTimeLiveUnit: TimeUnit = TimeUnit.DAYS,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()

        if (!connectionManager.isConnected()) {
            val cacheControl: CacheControl = CacheControl.Builder()
                .maxStale(ifNotConnectTimeLive, ifNotConnectTimeLiveUnit)
                .build()

            request = request.newBuilder()
                .removeHeader(HEADER_PRAGMA)
                .removeHeader(HEADER_CACHE_CONTROL)
                .cacheControl(cacheControl)
                .build()
        }

        return chain.proceed(request);
    }
}