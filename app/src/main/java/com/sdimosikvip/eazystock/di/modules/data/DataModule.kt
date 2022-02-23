package com.sdimosikvip.eazystock.di.modules

import com.sdimosikvip.data.db.FavouriteTickerDAO
import com.sdimosikvip.data.db.models.FavouriteTickerDB
import com.sdimosikvip.data.network.ConnectionManager
import com.sdimosikvip.data.network.finnhub.FinnhubService
import com.sdimosikvip.data.network.finnhub.models.StockCompanyResponse
import com.sdimosikvip.data.network.finnhub.models.StockPriceResponse
import com.sdimosikvip.data.network.mboum.MboumService
import com.sdimosikvip.data.network.mboum.models.MostWatchedTicketResponse
import com.sdimosikvip.data.repository.StockRepositoryImpl
import com.sdimosikvip.data.sources.local.FavouriteLocalSource
import com.sdimosikvip.data.sources.local.FavouriteLocalSourceImpl
import com.sdimosikvip.data.sources.remote.StockRemoteSource
import com.sdimosikvip.data.sources.remote.StockRemoteSourceImpl
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.FavouriteTickerDomain
import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.models.StockPriceDomain
import com.sdimosikvip.domain.models.TickersDomain
import com.sdimosikvip.domain.repository.StockRepository
import com.sdimosikvip.eazystock.di.modules.data.DatabaseModule
import com.sdimosikvip.eazystock.di.modules.data.MapperDataModule
import com.sdimosikvip.eazystock.di.modules.data.NetworkModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        NetworkModule::class,
        DatabaseModule::class,
        MapperDataModule::class
    ]
)
class DataModule {

    @Provides
    @Singleton
    fun provideStockRemoteSource(
        finnhubService: FinnhubService,
        mboumService: MboumService,
        stockCompanyMapper: BaseMapper<StockCompanyResponse, StockCompanyDomain>,
        stockPriceMapper: BaseMapper<StockPriceResponse, StockPriceDomain>,
        tickersMapper: BaseMapper<MostWatchedTicketResponse, TickersDomain>,
        connectionManager: ConnectionManager
    ): StockRemoteSource =
        StockRemoteSourceImpl(
            finnhubService,
            mboumService,
            stockCompanyMapper,
            stockPriceMapper,
            tickersMapper,
            connectionManager
        )

    @Provides
    @Singleton
    fun provideFavouriteLocalSource(
        favouriteTickerDao: FavouriteTickerDAO
    ): FavouriteLocalSource = FavouriteLocalSourceImpl(favouriteTickerDao)

    @Provides
    @Singleton
    fun provideStockRepository(
        remoteDataSource: StockRemoteSource,
        favouriteFavouriteLocalSource: FavouriteLocalSource,
        favouriteTickerDBMapper: BaseMapper<FavouriteTickerDB, FavouriteTickerDomain>
    ): StockRepository =
        StockRepositoryImpl(remoteDataSource, favouriteFavouriteLocalSource, favouriteTickerDBMapper)

}
