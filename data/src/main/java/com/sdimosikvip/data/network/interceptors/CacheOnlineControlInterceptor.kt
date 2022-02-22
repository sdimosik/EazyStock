package com.sdimosikvip.data.network.interceptors

import com.sdimosikvip.data.network.interceptors.Const.HEADER_CACHE_CONTROL
import com.sdimosikvip.data.network.interceptors.Const.HEADER_PRAGMA
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit


class CacheOnlineControlInterceptor(
    private val ifConnectTimeLive: Int = 60,
    private val ifConnectTimeLiveUnit: TimeUnit = TimeUnit.SECONDS,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        val cacheControl: CacheControl = CacheControl.Builder()
            .maxAge(ifConnectTimeLive, ifConnectTimeLiveUnit)
            .build()

        return response.newBuilder()
            .removeHeader(HEADER_PRAGMA)
            .removeHeader(HEADER_CACHE_CONTROL)
            .header(HEADER_CACHE_CONTROL, cacheControl.toString())
            .build()
    }
}