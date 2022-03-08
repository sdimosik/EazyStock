package com.sdimosikvip.data.sources.local

import com.sdimosikvip.data.db.favourite_tickers.FavouriteTickerDAO
import com.sdimosikvip.data.db.favourite_tickers.models.FavouriteTickerDB
import com.sdimosikvip.data.db.history_search.HistorySearchDAO
import com.sdimosikvip.data.db.history_search.models.HistoryTickerDB
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalSourceImpl @Inject constructor(
    private val dbFav: FavouriteTickerDAO,
    private val dbHistory: HistorySearchDAO,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : LocalSource {

    override fun getFavouriteStock(): Flow<List<FavouriteTickerDB>> =
        dbFav.getFavouriteStock().flowOn(defaultDispatcher)

    override suspend fun getOneFavouriteStock(ticker: String): FavouriteTickerDB? =
        withContext(defaultDispatcher) {
            dbFav.getOneFavouriteStock(ticker)
        }

    override suspend fun insertFavouriteStock(favouriteTickerDB: FavouriteTickerDB) =
        withContext(defaultDispatcher) {
            dbFav.insertFavouriteStock(favouriteTickerDB)
        }

    override suspend fun deleteFavoriteStock(favouriteTickerDB: FavouriteTickerDB) =
        withContext(defaultDispatcher) {
            dbFav.deleteFavoriteStock(favouriteTickerDB)
        }

    override suspend fun getFavouriteStockOneShot(): List<FavouriteTickerDB> =
        withContext(defaultDispatcher) {
            dbFav.getFavouriteStockOneshot()
        }

    override fun getHistory(): Flow<List<HistoryTickerDB>> =
        dbHistory.getHistory().flowOn(defaultDispatcher)

    override suspend fun addTickerWithAutoClean(historyTickerDB: HistoryTickerDB) =
        withContext(defaultDispatcher) {
            dbHistory.addTickerWithAutoClean(historyTickerDB)
        }
}