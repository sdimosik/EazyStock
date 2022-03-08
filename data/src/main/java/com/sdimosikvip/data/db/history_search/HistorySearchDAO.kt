package com.sdimosikvip.data.db.history_search

import androidx.room.*
import com.sdimosikvip.data.db.history_search.models.HistoryTickerDB
import kotlinx.coroutines.flow.Flow

@Dao
interface HistorySearchDAO {

    @Query("SELECT * FROM ${HistoryTickerDB.TABLE_NAME} ORDER BY timestamp DESC")
    fun getHistory(): Flow<List<HistoryTickerDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun justAddTicker(historyTickerDB: HistoryTickerDB)

    @Query(
        "DELETE FROM ${HistoryTickerDB.TABLE_NAME} WHERE ticker IN " +
                "(SELECT ticker FROM ${HistoryTickerDB.TABLE_NAME} ORDER BY timestamp DESC LIMIT 1 OFFSET 20)"
    )
    suspend fun removeOldTicker()

    @Transaction
    suspend fun addTickerWithAutoClean(historyTickerDB: HistoryTickerDB) {
        justAddTicker(historyTickerDB)
        removeOldTicker()
    }
}