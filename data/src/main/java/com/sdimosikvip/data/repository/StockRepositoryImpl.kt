package com.sdimosikvip.data.repository

import com.sdimosikvip.data.db.models.FavouriteTickerDB
import com.sdimosikvip.data.sources.local.FavouriteLocalSource
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
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
    private val net: StockRemoteSource,
    private val db: FavouriteLocalSource,
    private val favouriteTickerDBMapper: BaseMapper<FavouriteTickerDB, FavouriteTickerDomain>,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : StockRepository {

    override suspend fun getStocks(favouriteTickerDomainList: List<FavouriteTickerDomain>) =
        flow {
            coroutineScope {
                val list: MutableList<StockItemDomain> = mutableListOf()

                val listDef = mutableListOf<Deferred<Any>>()
                for (ticker in favouriteTickerDomainList) {
                    listDef.add(
                        async {
                            val stockDomain = getStock(ticker.ticker)
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

    override suspend fun saveFavouriteStock(favouriteTickerDomain: FavouriteTickerDomain) =
        db.insertFavouriteStock(
            favouriteTickerDBMapper.reverseTransform(favouriteTickerDomain)
        )


    override suspend fun deleteFavouriteStock(favouriteTickerDomain: FavouriteTickerDomain) =
        db.deleteFavoriteStock(
            favouriteTickerDBMapper.reverseTransform(favouriteTickerDomain)
        )


    override suspend fun getFavouriteStocks(): Flow<List<FavouriteTickerDomain>> =
        db.getFavouriteStock().map { list ->
            list.map { favouriteTickerDBMapper.transform(it) }
        }.flowOn(defaultDispatcher)
}
