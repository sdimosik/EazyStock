package com.sdimosikvip.eazystock.di.modules.data

import android.content.Context
import androidx.room.Room
import com.sdimosikvip.data.db.cache.StockDAO
import com.sdimosikvip.data.db.StockDatabase
import com.sdimosikvip.data.db.favourite.FavouriteTickerDAO
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideStockDatabase(context: Context): StockDatabase {
        return Room.databaseBuilder(
            context,
            StockDatabase::class.java,
            "StockDatabase.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideStockDAO(database: StockDatabase): StockDAO {
        return database.stockDAO()
    }

    @Singleton
    @Provides
    fun provideFavouriteTickerDAO(database: StockDatabase): FavouriteTickerDAO {
        return database.favouriteTickerDAO()
    }
}