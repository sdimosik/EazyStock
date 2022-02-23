package com.sdimosikvip.data.repository

import android.util.Log
import com.sdimosikvip.data.db.cache.StockDAO
import com.sdimosikvip.data.db.cache.models.CompanyInfoDB
import com.sdimosikvip.data.db.cache.models.StockDB
import com.sdimosikvip.data.db.favourite.FavouriteTickerDAO
import com.sdimosikvip.data.db.favourite.models.FavouriteTickerDB
import com.sdimosikvip.data.sources.StockRemoteSource
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.FavouriteTickerDomain
import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.models.StockItemDomain
import com.sdimosikvip.domain.repository.StockRepository
import com.sdimosikvip.domain.requireValue
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
    private val remoteDataSource: StockRemoteSource,
    private val cacheDataSource: StockDAO,
    private val favouriteDataSource: FavouriteTickerDAO,
    private val stockItemDbMapper: BaseMapper<StockDB, StockItemDomain>,
    private val stockCompanyDbMapper: BaseMapper<CompanyInfoDB, StockCompanyDomain>,
    private val favouriteTickerDBMapper: BaseMapper<FavouriteTickerDB, FavouriteTickerDomain>,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val timestampDeltaUpdate: Long = 60
) : StockRepository {

    override fun updateOrLoadStocks(tickers: List<String>) =
        flow {
            coroutineScope {
                Log.d("rep", "updateOrLoadStocks")
                val currentTimestamp =
                    withContext(defaultDispatcher) { System.currentTimeMillis() / 1000 }

                val localStockList = withContext(defaultDispatcher) { cacheDataSource.getStocks() }

                val list: MutableList<StockItemDomain> = mutableListOf()
                val listDef = mutableListOf<Deferred<Any>>()
                tickers.forEach { loadTicker ->
                    localStockList.find { it.tickerId == loadTicker }.also { cacheTicker ->
                        if (cacheTicker == null) {
                            listDef.add(
                                async {
                                    val stockDomain = getStock(loadTicker, false)
                                    withContext(defaultDispatcher) {
                                        cacheDataSource.insertStock(
                                            stockItemDbMapper.reverseTransform(stockDomain)
                                        )
                                    }
                                    synchronized(list) {
                                        list.add(stockDomain)
                                    }
                                })
                        } else {
                            if (currentTimestamp - cacheTicker.timestamp < timestampDeltaUpdate) {
                                return@also
                            }
                            listDef.add(
                                async {
                                    val stockDomain = withContext(defaultDispatcher) {
                                        val priceStock =
                                            remoteDataSource.getPriceStock(cacheTicker.tickerId)
                                                .requireValue()
                                        return@withContext StockItemDomain(
                                            stockCompanyDbMapper.transform(cacheTicker.companyInfo),
                                            priceStock,
                                            cacheTicker.isFavourite
                                        )
                                    }
                                    withContext(defaultDispatcher) {
                                        cacheDataSource.insertStock(
                                            stockItemDbMapper.reverseTransform(stockDomain)
                                        )
                                    }
                                    synchronized(list) {
                                        list.add(stockDomain)
                                    }
                                }
                            )
                        }
                    }
                }

                listDef.awaitAll()

                list.sortBy { it.stockCompanyDomain.ticker }
                emit(list)
            }
        }.flowOn(defaultDispatcher)

    private suspend fun getStock(ticker: String, isFavourite: Boolean) =
        withContext(defaultDispatcher) {
            Log.d("rep", "getStock")
            val companyStock = remoteDataSource.getCompanyStock(ticker).requireValue()
            val priceStock = remoteDataSource.getPriceStock(ticker).requireValue()
            return@withContext StockItemDomain(
                companyStock,
                priceStock,
                isFavourite
            )
        }

    override suspend fun saveStock(stockItemDomain: StockItemDomain) =
        withContext(defaultDispatcher) {
            Log.d("rep", "saveStock")
            cacheDataSource.insertStock(
                stockItemDbMapper.reverseTransform(stockItemDomain)
            )
        }

    override suspend fun deleteSaveStock(stockItemDomain: StockItemDomain) =
        withContext(defaultDispatcher) {
            cacheDataSource.deleteStock(
                stockItemDbMapper.reverseTransform(stockItemDomain)
            )
        }

    override suspend fun getCacheStocks(): List<StockItemDomain> = withContext(defaultDispatcher) {
        cacheDataSource.getStocks().map { stockItemDbMapper.transform(it) }
    }

    override fun getCacheStocksFlow(): Flow<List<StockItemDomain>> =
        cacheDataSource.getStocksFlow().map {
            Log.d("rep", "getCacheStocks")
            it.map { stockDB ->
                stockItemDbMapper.transform(stockDB)
            }
        }.flowOn(defaultDispatcher)

    override fun getCacheStocksFlow(listTicker: List<String>): Flow<List<StockItemDomain>> =
        cacheDataSource.getStocksFlow().map {
            Log.d("rep", "getCacheStocks")
            it
                .filter { stockDB -> listTicker.find { stockDB.tickerId == it } != null }
                .map { stockDB ->
                    stockItemDbMapper.transform(stockDB)
                }
        }.flowOn(defaultDispatcher)

    override fun getFavouriteStocksFlow(): Flow<List<StockItemDomain>> =
        cacheDataSource.getStocksFlow().map {
            Log.d("rep", "getFavouriteStocks")
            it
                .filter { it.isFavourite }
                .map { stockDB ->
                    stockItemDbMapper.transform(stockDB)
                }
        }.flowOn(defaultDispatcher)

    override fun getFavouriteTickersFlow(): Flow<List<FavouriteTickerDomain>> =
        favouriteDataSource.getFavouriteTickersFlow().map {
            it.map { favouriteTickerDBMapper.transform(it) }
        }

    override suspend fun saveFavouriteTickers(favouriteTickerDomain: FavouriteTickerDomain) =
        withContext(defaultDispatcher) {
            favouriteDataSource.insertFavouriteTicker(
                favouriteTickerDBMapper.reverseTransform(
                    favouriteTickerDomain
                )
            )
        }

    override suspend fun deleteFavouriteTickers(favouriteTickerDomain: FavouriteTickerDomain) =
        withContext(defaultDispatcher) {
            favouriteDataSource.deleteFavouriteTicker(
                favouriteTickerDBMapper.reverseTransform(
                    favouriteTickerDomain
                )
            )
        }
}
