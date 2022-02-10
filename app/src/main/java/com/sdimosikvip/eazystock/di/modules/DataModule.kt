package com.sdimosikvip.eazystock.di.modules

import com.sdimosikvip.data.BuildConfig
import com.sdimosikvip.data.network.StockService
import com.sdimosikvip.data.network.interceptors.AuthInterceptor
import com.sdimosikvip.data.network.mapper.StockCompanyMapper
import com.sdimosikvip.data.network.models.StockCompanyResponse
import com.sdimosikvip.data.repository.StockRepositoryImpl
import com.sdimosikvip.data.sources.StockRemoteSource
import com.sdimosikvip.data.sources.StockRemoteSourceImpl
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.repository.StockRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthInterceptor(BuildConfig.API_TOKEN))
            .build()
    }

    @Singleton
    @Provides
    fun provideStockService(okHttpClient: OkHttpClient): StockService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(StockService::class.java)

    }

    @Provides
    fun provideStockCompanyResponseToStockCompanyDomain(): BaseMapper<StockCompanyResponse, StockCompanyDomain> =
        StockCompanyMapper()

    @Provides
    fun provideStockRemoteSource(
        stockService: StockService,
        stockCompanyMapper: BaseMapper<StockCompanyResponse, StockCompanyDomain>
    ): StockRemoteSource = StockRemoteSourceImpl(stockService, stockCompanyMapper)

    @Provides
    @Reusable
    fun provideStockRepository(
        remoteDataSource: StockRemoteSource
    ): StockRepository = StockRepositoryImpl(remoteDataSource)

}