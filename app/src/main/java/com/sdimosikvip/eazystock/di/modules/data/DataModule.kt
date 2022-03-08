package com.sdimosikvip.eazystock.di.modules

import com.sdimosikvip.data.db.favourite_tickers.FavouriteTickerDAO
import com.sdimosikvip.data.db.favourite_tickers.models.FavouriteTickerDB
import com.sdimosikvip.data.db.history_search.HistorySearchDAO
import com.sdimosikvip.data.db.history_search.mapper.HistoryTickerDBMapper
import com.sdimosikvip.data.db.history_search.models.HistoryTickerDB
import com.sdimosikvip.data.network.ConnectionManager
import com.sdimosikvip.data.network.finnhub.FinnhubActualService
import com.sdimosikvip.data.network.finnhub.FinnhubCommonService
import com.sdimosikvip.data.network.finnhub.models.StockCompanyResponse
import com.sdimosikvip.data.network.finnhub.models.StockPriceResponse
import com.sdimosikvip.data.network.finnhub.models.SymbolLookupResponse
import com.sdimosikvip.data.network.mboum.MboumService
import com.sdimosikvip.data.network.mboum.models.MostWatchedTicketResponse
import com.sdimosikvip.data.repository.StockRepositoryImpl
import com.sdimosikvip.data.sources.local.LocalSource
import com.sdimosikvip.data.sources.local.LocalSourceImpl
import com.sdimosikvip.data.sources.remote.StockRemoteSource
import com.sdimosikvip.data.sources.remote.StockRemoteSourceImpl
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.*
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
        finnhubActualService: FinnhubActualService,
        finnhubCommonService: FinnhubCommonService,
        mboumService: MboumService,
        stockCompanyMapper: BaseMapper<StockCompanyResponse, StockCompanyDomain>,
        stockPriceMapper: BaseMapper<StockPriceResponse, StockPriceDomain>,
        tickersMapper: BaseMapper<MostWatchedTicketResponse, TickersDomain>,
        symbolLookupResponseMapper: BaseMapper<SymbolLookupResponse, SymbolLookupDomain>,
        connectionManager: ConnectionManager
    ): StockRemoteSource =
        StockRemoteSourceImpl(
            finnhubActualService,
            finnhubCommonService,
            mboumService,
            stockCompanyMapper,
            stockPriceMapper,
            tickersMapper,
            symbolLookupResponseMapper,
            connectionManager
        )

    @Provides
    @Singleton
    fun provideFavouriteLocalSource(
        favouriteTickerDao: FavouriteTickerDAO,
        historySearchDAO: HistorySearchDAO
    ): LocalSource = LocalSourceImpl(favouriteTickerDao, historySearchDAO)

    @Provides
    @Singleton
    fun provideStockRepository(
        remoteDataSource: StockRemoteSource,
        favouriteLocalSource: LocalSource,
        favouriteTickerDBMapper: BaseMapper<FavouriteTickerDB, FavouriteTickerDomain>,
        historyTickerDBMapper: BaseMapper<HistoryTickerDB, TickerDomain>
    ): StockRepository =
        StockRepositoryImpl(
            remoteDataSource,
            favouriteLocalSource,
            favouriteTickerDBMapper,
            historyTickerDBMapper
        )

}
