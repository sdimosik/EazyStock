package com.sdimosikvip.eazystock.di.modules.data

import android.content.Context
import androidx.room.Room
import com.sdimosikvip.data.db.favourite_tickers.FavouriteTickerDAO
import com.sdimosikvip.data.db.Database
import com.sdimosikvip.data.db.history_search.HistorySearchDAO
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideFavouriteStockDatabase(context: Context): Database {
        return Room.databaseBuilder(
            context,
            Database::class.java,
            "FavouriteStockDatabase.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideFavouriteStockDAO(database: Database): FavouriteTickerDAO {
        return database.favouriteStockDAO()
    }

    @Singleton
    @Provides
    fun provideSearchHistoryDAO(database: Database): HistorySearchDAO {
        return database.historySearchDAO()
    }

}