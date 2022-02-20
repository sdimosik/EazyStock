package com.sdimosikvip.eazystock.di.modules

import android.content.Context
import androidx.room.Room
import com.sdimosikvip.data.BuildConfig
import com.sdimosikvip.data.db.FavouriteStockDAO
import com.sdimosikvip.data.db.FavouriteStockDatabase
import com.sdimosikvip.data.network.ConnectionManager
import com.sdimosikvip.data.network.finnhub.FinnhubService
import com.sdimosikvip.data.network.interceptors.AuthInterceptor
import com.sdimosikvip.data.network.interceptors.NetworkStatusInterceptor
import com.sdimosikvip.data.network.finnhub.mapper.StockCompanyMapper
import com.sdimosikvip.data.network.finnhub.mapper.StockPriceMapper
import com.sdimosikvip.data.network.finnhub.models.StockCompanyResponse
import com.sdimosikvip.data.network.finnhub.models.StockPriceResponse
import com.sdimosikvip.data.network.interceptors.CacheControlInterceptor
import com.sdimosikvip.data.network.mboum.MboumService
import com.sdimosikvip.data.network.mboum.mapper.TickersMapper
import com.sdimosikvip.data.network.mboum.models.MostWatchedTicketResponse
import com.sdimosikvip.data.repository.StockRepositoryImpl
import com.sdimosikvip.data.sources.StockRemoteSource
import com.sdimosikvip.data.sources.StockRemoteSourceImpl
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.models.StockPriceDomain
import com.sdimosikvip.domain.models.TickersDomain
import com.sdimosikvip.domain.repository.StockRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideFavouriteStockDatabase(context: Context): FavouriteStockDatabase {
        return Room.databaseBuilder(
            context,
            FavouriteStockDatabase::class.java,
            "FavouriteStockDatabase.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideFavouriteStockDAO(database: FavouriteStockDatabase): FavouriteStockDAO {
        return database.favouriteStockDAO()
    }

    @Singleton
    @Provides
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Singleton
    @Provides
    fun provideNetworkStatusInterceptorInterceptor(
        connectionManager: ConnectionManager
    ): NetworkStatusInterceptor = NetworkStatusInterceptor(connectionManager)

    @Singleton
    @Provides
    fun provideCacheControlInterceptor(
        connectionManager: ConnectionManager
    ): CacheControlInterceptor = CacheControlInterceptor(connectionManager)

    @Singleton
    @Finnhub
    @Provides
    fun provideOkHttpClientFinnhub(
        loggingInterceptor: HttpLoggingInterceptor,
        networkStatusInterceptor: NetworkStatusInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(networkStatusInterceptor)
            .addInterceptor(loggingInterceptor)
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
        networkStatusInterceptor: NetworkStatusInterceptor,
        cacheControlInterceptor: CacheControlInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(cacheControlInterceptor)
            .addInterceptor(networkStatusInterceptor)
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

    @Provides
    fun provideStockCompanyResponseToStockCompanyDomain(): BaseMapper<StockCompanyResponse, StockCompanyDomain> =
        StockCompanyMapper()

    @Provides
    fun provideStockPriceResponseToStockPriceDomain(): BaseMapper<StockPriceResponse, StockPriceDomain> =
        StockPriceMapper()

    @Provides
    fun provideTickersMapper(): BaseMapper<MostWatchedTicketResponse, TickersDomain> =
        TickersMapper()

    @Provides
    fun provideStockRemoteSource(
        finnhubService: FinnhubService,
        mboumService: MboumService,
        stockCompanyMapper: BaseMapper<StockCompanyResponse, StockCompanyDomain>,
        stockPriceMapper: BaseMapper<StockPriceResponse, StockPriceDomain>,
        tickersMapper: BaseMapper<MostWatchedTicketResponse, TickersDomain>
    ): StockRemoteSource =
        StockRemoteSourceImpl(
            finnhubService,
            mboumService,
            stockCompanyMapper,
            stockPriceMapper,
            tickersMapper
        )

    @Provides
    @Reusable
    fun provideStockRepository(
        remoteDataSource: StockRemoteSource,
        favouriteStockDao: FavouriteStockDAO
    ): StockRepository = StockRepositoryImpl(remoteDataSource, favouriteStockDao)

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Finnhub

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Mboum
