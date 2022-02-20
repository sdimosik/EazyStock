package com.sdimosikvip.data.network.finnhub.interceptors

import com.sdimosikvip.data.network.ConnectionManager
import com.sdimosikvip.domain.NoConnectionException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkStatusInterceptor @Inject constructor(
    private val connectionManager: ConnectionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return if (connectionManager.isConnected()) {
            chain.proceed(chain.request())
        } else {
            throw NoConnectionException()
        }
    }
}