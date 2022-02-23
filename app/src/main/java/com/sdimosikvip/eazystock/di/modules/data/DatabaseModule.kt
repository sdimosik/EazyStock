package com.sdimosikvip.eazystock.di.modules.data

import android.content.Context
import androidx.room.Room
import com.sdimosikvip.data.db.FavouriteStockDAO
import com.sdimosikvip.data.db.FavouriteStockDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideFavouriteStockDatabase(context: Context): FavouriteStockDatabase {
        return Room.databaseBuilder(
            context,
            FavouriteStockDatabase::class.java,
            "FavouriteStockDatabase.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideFavouriteStockDAO(database: FavouriteStockDatabase): FavouriteStockDAO {
        return database.favouriteStockDAO()
    }

}