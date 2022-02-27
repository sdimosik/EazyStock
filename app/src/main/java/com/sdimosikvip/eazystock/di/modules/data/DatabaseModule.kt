package com.sdimosikvip.eazystock.di.modules.data

import android.content.Context
import androidx.room.Room
import com.sdimosikvip.data.db.FavouriteTickerDAO
import com.sdimosikvip.data.db.FavouriteTickerDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideFavouriteStockDatabase(context: Context): FavouriteTickerDatabase {
        return Room.databaseBuilder(
            context,
            FavouriteTickerDatabase::class.java,
            "FavouriteStockDatabase.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideFavouriteStockDAO(database: FavouriteTickerDatabase): FavouriteTickerDAO {
        return database.favouriteStockDAO()
    }

}