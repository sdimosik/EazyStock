package com.sdimosikvip.eazystock.di.modules.data

import android.content.Context
import com.sdimosikvip.data.BuildConfig
import com.sdimosikvip.data.network.ConnectionManager
import com.sdimosikvip.data.network.finnhub.FinnhubService
import com.sdimosikvip.data.network.interceptors.AuthInterceptor
import com.sdimosikvip.data.network.interceptors.CacheOfflineControlInterceptor
import com.sdimosikvip.data.network.interceptors.CacheOnlineControlInterceptor
import com.sdimosikvip.data.network.interceptors.NetworkStatusInterceptor
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
    @Provides
    fun provideCacheOnlineControlInterceptor(): CacheOnlineControlInterceptor =
        CacheOnlineControlInterceptor()

    @Singleton
    @Provides
    fun provideCacheOfflineControlInterceptor(
        connectionManager: ConnectionManager
    ): CacheOfflineControlInterceptor = CacheOfflineControlInterceptor(connectionManager)

    @Singleton
    @Provides
    fun provideCacheOkHttp(context: Context): Cache {
        val httpCacheDirectory = File(context.cacheDir, "http-cache")
        val cacheSize = 25 * 1024 * 1024 // 25 MiB
        return Cache(httpCacheDirectory, cacheSize.toLong())
    }

    @Singleton
    @Finnhub
    @Provides
    fun provideOkHttpClientFinnhub(
        loggingInterceptor: HttpLoggingInterceptor,
        cacheOnlineControlInterceptor: CacheOnlineControlInterceptor,
        cacheOfflineControlInterceptor: CacheOfflineControlInterceptor,
        cache: Cache,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(cacheOnlineControlInterceptor)
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
    fun provideFinnhubService(@Finnhub okHttpClient: OkHttpClient): FinnhubService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_FINNHUB)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(FinnhubService::class.java)
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