package com.sdimosikvip.eazystock.di.modules

import android.content.Context
import com.sdimosikvip.data.BuildConfig
import com.sdimosikvip.data.network.ConnectionManager
import com.sdimosikvip.data.network.finnhub.FinnhubService
import com.sdimosikvip.data.network.finnhub.interceptors.AuthInterceptor
import com.sdimosikvip.data.network.finnhub.interceptors.NetworkStatusInterceptor
import com.sdimosikvip.data.network.finnhub.mapper.StockCompanyMapper
import com.sdimosikvip.data.network.finnhub.mapper.StockPriceMapper
import com.sdimosikvip.data.network.finnhub.models.StockCompanyResponse
import com.sdimosikvip.data.network.finnhub.models.StockPriceResponse
import com.sdimosikvip.data.repository.StockRepositoryImpl
import com.sdimosikvip.data.sources.StockRemoteSource
import com.sdimosikvip.data.sources.StockRemoteSourceImpl
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.models.StockPriceDomain
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
        loggingInterceptor: HttpLoggingInterceptor,
        context: Context
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(NetworkStatusInterceptor(ConnectionManager(context)))
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthInterceptor(BuildConfig.API_TOKEN))
            .build()
    }

    @Singleton
    @Provides
    fun provideStockService(okHttpClient: OkHttpClient): FinnhubService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(FinnhubService::class.java)
    }

    @Provides
    fun provideStockCompanyResponseToStockCompanyDomain(): BaseMapper<StockCompanyResponse, StockCompanyDomain> =
        StockCompanyMapper()

    @Provides
    fun provideStockPriceResponseToStockPriceDomain(): BaseMapper<StockPriceResponse, StockPriceDomain> =
        StockPriceMapper()

    @Provides
    fun provideStockRemoteSource(
        finnhubService: FinnhubService,
        stockCompanyMapper: BaseMapper<StockCompanyResponse, StockCompanyDomain>,
        stockPriceMapper: BaseMapper<StockPriceResponse, StockPriceDomain>,
    ): StockRemoteSource = StockRemoteSourceImpl(finnhubService, stockCompanyMapper, stockPriceMapper)

    @Provides
    @Reusable
    fun provideStockRepository(
        remoteDataSource: StockRemoteSource
    ): StockRepository = StockRepositoryImpl(remoteDataSource)

}