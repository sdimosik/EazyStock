package com.sdimosikvip.eazystock.di.modules

import com.sdimosikvip.data.db.cache.StockDAO
import com.sdimosikvip.data.db.cache.models.CompanyInfoDB
import com.sdimosikvip.data.db.cache.models.StockDB
import com.sdimosikvip.data.db.favourite.FavouriteTickerDAO
import com.sdimosikvip.data.db.favourite.mapper.FavouriteTickerDBMapper
import com.sdimosikvip.data.db.favourite.models.FavouriteTickerDB
import com.sdimosikvip.data.network.finnhub.FinnhubService
import com.sdimosikvip.data.network.finnhub.models.StockCompanyResponse
import com.sdimosikvip.data.network.finnhub.models.StockPriceResponse
import com.sdimosikvip.data.network.mboum.MboumService
import com.sdimosikvip.data.network.mboum.models.MostWatchedTicketResponse
import com.sdimosikvip.data.repository.StockRepositoryImpl
import com.sdimosikvip.data.sources.StockRemoteSource
import com.sdimosikvip.data.sources.StockRemoteSourceImpl
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.*
import com.sdimosikvip.domain.repository.StockRepository
import com.sdimosikvip.eazystock.di.modules.data.DataMappersModule
import com.sdimosikvip.eazystock.di.modules.data.DatabaseModule
import com.sdimosikvip.eazystock.di.modules.data.NetworkModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        NetworkModule::class,
        DatabaseModule::class,
        DataMappersModule::class,
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
    @Singleton
    fun provideStockRepository(
        remoteDataSource: StockRemoteSource,
        stockDao: StockDAO,
        favouriteTickerDAO: FavouriteTickerDAO,
        stockItemDbMapper: BaseMapper<StockDB, StockItemDomain>,
        stockCompanyDbMapper: BaseMapper<CompanyInfoDB, StockCompanyDomain>,
        favouriteTickerDBMapper: BaseMapper<FavouriteTickerDB, FavouriteTickerDomain>,
    ): StockRepository = StockRepositoryImpl(
        remoteDataSource,
        stockDao,
        favouriteTickerDAO,
        stockItemDbMapper,
        stockCompanyDbMapper,
        favouriteTickerDBMapper
    )

}
