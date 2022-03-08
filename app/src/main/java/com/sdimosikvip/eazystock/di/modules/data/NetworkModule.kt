package com.sdimosikvip.eazystock.di.modules.data

import android.content.Context
import com.sdimosikvip.data.BuildConfig
import com.sdimosikvip.data.network.ConnectionManager
import com.sdimosikvip.data.network.finnhub.FinnhubActualService
import com.sdimosikvip.data.network.finnhub.FinnhubCommonService
import com.sdimosikvip.data.network.interceptors.AuthInterceptor
import com.sdimosikvip.data.network.interceptors.CacheOfflineControlInterceptor
import com.sdimosikvip.data.network.interceptors.CacheOnlineControlInterceptor
import com.sdimosikvip.data.network.mboum.MboumService
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Singleton
    @OnlineCacheShort
    @Provides
    fun provideCacheOnlineShortControlInterceptor(): CacheOnlineControlInterceptor =
        CacheOnlineControlInterceptor(
            ifConnectTimeLive = 60,
            ifConnectTimeLiveUnit = TimeUnit.SECONDS
        )

    @Singleton
    @OnlineCacheLong
    @Provides
    fun provideCacheOnlineLongControlInterceptor(): CacheOnlineControlInterceptor =
        CacheOnlineControlInterceptor(
            ifConnectTimeLive = 365,
            ifConnectTimeLiveUnit = TimeUnit.DAYS
        )

    @Singleton
    @Provides
    fun provideCacheOfflineControlInterceptor(
        connectionManager: ConnectionManager
    ): CacheOfflineControlInterceptor = CacheOfflineControlInterceptor(
        connectionManager = connectionManager,
        ifNotConnectTimeLive = 365,
        ifNotConnectTimeLiveUnit = TimeUnit.DAYS
    )

    @Singleton
    @Provides
    fun provideCacheOkHttp(context: Context): Cache {
        val httpCacheDirectory = File(context.cacheDir, "http-cache")
        val cacheSize = 25 * 1024 * 1024 // 25 MiB
        return Cache(httpCacheDirectory, cacheSize.toLong())
    }

    @Singleton
    @FinnhubActual
    @Provides
    fun provideOkHttpClientFinnhubActual(
        loggingInterceptor: HttpLoggingInterceptor,
        @OnlineCacheShort cacheOnlineShortControlInterceptor: CacheOnlineControlInterceptor,
        cacheOfflineControlInterceptor: CacheOfflineControlInterceptor,
        cache: Cache,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(cacheOnlineShortControlInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(cacheOfflineControlInterceptor)
            .addInterceptor(
                AuthInterceptor(
                    BuildConfig.PARAMETR_API_TOKET_FINNHUB,
                    BuildConfig.API_TOKEN_FINNHUB
                )
            )
            .build()
    }

    @Singleton
    @FinnhubCommon
    @Provides
    fun provideOkHttpClientFinnhubCommon(
        loggingInterceptor: HttpLoggingInterceptor,
        @OnlineCacheLong cacheOnlineLongControlInterceptor: CacheOnlineControlInterceptor,
        cacheOfflineControlInterceptor: CacheOfflineControlInterceptor,
        cache: Cache,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(cacheOnlineLongControlInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(cacheOfflineControlInterceptor)
            .addInterceptor(
                AuthInterceptor(
                    BuildConfig.PARAMETR_API_TOKET_FINNHUB,
                    BuildConfig.API_TOKEN_FINNHUB
                )
            )
            .build()
    }

    @Singleton
    @Mboum
    @Provides
    fun provideOkHttpClientMboum(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(
                AuthInterceptor(
                    BuildConfig.PARAMETR_API_TOKET_MBOUM,
                    BuildConfig.API_TOKEN_MBOUM
                )
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideFinnhubActualService(@FinnhubActual okHttpClient: OkHttpClient): FinnhubActualService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_FINNHUB)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(FinnhubActualService::class.java)
    }

    @Singleton
    @Provides
    fun provideFinnhubCommonService(@FinnhubCommon okHttpClient: OkHttpClient): FinnhubCommonService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_FINNHUB)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(FinnhubCommonService::class.java)
    }

    @Singleton
    @Provides
    fun provideMboumService(@Mboum okHttpClient: OkHttpClient): MboumService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_MBOUM)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MboumService::class.java)
    }
}