package com.sdimosikvip.data.db.cache

import androidx.room.*
import com.sdimosikvip.data.db.cache.models.StockDB
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDAO {

    @Query("SELECT * FROM ${StockDB.TABLE_NAME}")
    fun getStocksFlow(): Flow<List<StockDB>>

    @Query("SELECT * FROM ${StockDB.TABLE_NAME}")
    suspend fun getStocks(): List<StockDB>

    @Query("SELECT * FROM ${StockDB.TABLE_NAME} WHERE tickerId = :ticker")
    suspend fun getOneStock(ticker: String): StockDB?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStock(stockDB: StockDB)

    @Delete
    suspend fun deleteStock(stockDB: StockDB)
}