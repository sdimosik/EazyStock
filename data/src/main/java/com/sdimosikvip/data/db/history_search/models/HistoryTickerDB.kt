package com.sdimosikvip.data.db.history_search.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = HistoryTickerDB.TABLE_NAME)
data class HistoryTickerDB(
    @PrimaryKey
    val ticker: String,
    val timestamp: Long
) {

    companion object {
        const val TABLE_NAME = "search_history"
    }
}
