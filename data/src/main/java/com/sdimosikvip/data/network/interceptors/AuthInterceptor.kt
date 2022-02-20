package com.sdimosikvip.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val parameterName: String,
    private val apiToken: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val httpUrl = original.url.newBuilder()
            .addQueryParameter(parameterName, apiToken)
            .build()

        val requestBuilder = original.newBuilder()
            .url(httpUrl)

        return chain.proceed(requestBuilder.build())
    }
}