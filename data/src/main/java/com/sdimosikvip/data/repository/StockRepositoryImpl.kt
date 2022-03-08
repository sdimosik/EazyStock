package com.sdimosikvip.data.repository

import com.sdimosikvip.data.db.favourite_tickers.models.FavouriteTickerDB
import com.sdimosikvip.data.db.history_search.models.HistoryTickerDB
import com.sdimosikvip.data.sources.local.LocalSource
import com.sdimosikvip.data.sources.remote.StockRemoteSource
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.*
import com.sdimosikvip.domain.repository.StockRepository
import com.sdimosikvip.domain.requireValue
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
    private val net: StockRemoteSource,
    private val db: LocalSource,
    private val favouriteTickerDBMapper: BaseMapper<FavouriteTickerDB, FavouriteTickerDomain>,
    private val historyTickerDBMapper: BaseMapper<HistoryTickerDB, TickerDomain>,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : StockRepository {

    companion object {
        const val TAG = "StockRepositoryImpl"
    }

    override fun getStocks(favouriteTickerDomainList: List<FavouriteTickerDomain>) =
        flow {
            coroutineScope {
                val list: MutableList<StockItemDomain> = mutableListOf()

                val listDef = mutableListOf<Deferred<Any>>()
                for (ticker in favouriteTickerDomainList) {
                    listDef.add(
                        async {
                            val stockDomain = getNullableStock(ticker.ticker) ?: return@async
                            synchronized(list) {
                                list.add(stockDomain)
                            }
                        })
                }
                listDef.awaitAll()

                list.sortBy { it.stockCompanyDomain.ticker }
                emit(list)
            }
        }.flowOn(defaultDispatcher)

    private suspend fun getStock(ticker: String): StockItemDomain = withContext(defaultDispatcher) {

        val isFavourite = db.getOneFavouriteStock(ticker) != null
        val companyStock = net.getCompanyStock(ticker).requireValue()
        val priceStock = net.getPriceStock(ticker).requireValue()

        StockItemDomain(
            companyStock,
            priceStock,
            isFavourite
        )
    }

    private suspend fun getNullableStock(ticker: String): StockItemDomain? =
        withContext(defaultDispatcher) {

            val isFavourite = db.getOneFavouriteStock(ticker) != null

            val companyStock = net.getNullableCompanyStock(ticker) ?: return@withContext null
            val company = companyStock.requireValue()
            if (company.currency == null ||
                company.currency?.isEmpty() == true ||
                company.country == null ||
                company.country?.isEmpty() == true
            ) return@withContext null

            val priceStock = net.getNullablePriceStock(ticker) ?: return@withContext null
            val price = priceStock.requireValue()

            StockItemDomain(
                company,
                price,
                isFavourite
            )
        }

    override suspend fun saveFavouriteStock(favouriteTickerDomain: FavouriteTickerDomain) =
        db.insertFavouriteStock(
            favouriteTickerDBMapper.reverseTransform(favouriteTickerDomain)
        )


    override suspend fun deleteFavouriteStock(favouriteTickerDomain: FavouriteTickerDomain) =
        db.deleteFavoriteStock(
            favouriteTickerDBMapper.reverseTransform(favouriteTickerDomain)
        )


    override fun getFavouriteStocks(): Flow<List<FavouriteTickerDomain>> =
        db.getFavouriteStock().map { list ->
            list.map { favouriteTickerDBMapper.transform(it) }
        }.flowOn(defaultDispatcher)

    override suspend fun getFavouriteStocksOneshot(): List<FavouriteTickerDomain> =
        db.getFavouriteStockOneShot().map { favouriteTickerDBMapper.transform(it) }

    override fun searchTicker(text: String) = flow {
        coroutineScope {

            if (text == "") {
                emit(mutableListOf())
                return@coroutineScope
            }

            val list: MutableList<StockItemDomain> = mutableListOf()

            val symbolSearch = net.searchSymbol(text).requireValue().result
                .filter {
                    it.type != null && it.type == "Common Stock"
                }

            val listDef = mutableListOf<Deferred<Any>>()
            for (search in symbolSearch) {
                listDef.add(
                    async {
                        val stockDomain = getNullableStock(search.symbol) ?: return@async
                        synchronized(list) {
                            list.add(stockDomain)
                        }
                    })
            }
            listDef.awaitAll()

            list.sortBy { it.stockCompanyDomain.ticker }

            Timber.tag(TAG).d("list size: ${list.size}")
            emit(list)
        }
    }.flowOn(defaultDispatcher)

    override fun getHistorySearch(): Flow<List<TickerDomain>> =
        db.getHistory().map {
            it.map { historyTickerDBMapper.transform(it) }
        }

    override suspend fun saveHistoryTickers(tickerDomain: TickerDomain) =
        withContext(defaultDispatcher) {
            db.addTickerWithAutoClean(historyTickerDBMapper.reverseTransform(tickerDomain))
        }

}
