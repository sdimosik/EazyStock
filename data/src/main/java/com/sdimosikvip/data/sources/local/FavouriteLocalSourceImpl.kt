package com.sdimosikvip.data.sources.local

import com.sdimosikvip.data.db.FavouriteTickerDAO
import com.sdimosikvip.data.db.models.FavouriteTickerDB
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavouriteLocalSourceImpl @Inject constructor(
    private val db: FavouriteTickerDAO,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : FavouriteLocalSource {

    override fun getFavouriteStock(): Flow<List<FavouriteTickerDB>> =
        db.getFavouriteStock().flowOn(defaultDispatcher)

    override suspend fun getOneFavouriteStock(ticker: String): FavouriteTickerDB? =
        withContext(defaultDispatcher) {
            db.getOneFavouriteStock(ticker)
        }

    override suspend fun insertFavouriteStock(favouriteTickerDB: FavouriteTickerDB) =
        withContext(defaultDispatcher) {
            db.insertFavouriteStock(favouriteTickerDB)
        }

    override suspend fun deleteFavoriteStock(favouriteTickerDB: FavouriteTickerDB) =
        withContext(defaultDispatcher) {
            db.deleteFavoriteStock(favouriteTickerDB)
        }

}