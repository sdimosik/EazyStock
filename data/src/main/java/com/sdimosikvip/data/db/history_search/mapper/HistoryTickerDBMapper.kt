package com.sdimosikvip.data.db.history_search.mapper

import com.sdimosikvip.data.db.history_search.models.HistoryTickerDB
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.TickerDomain

class HistoryTickerDBMapper : BaseMapper<HistoryTickerDB, TickerDomain> {
    override fun transform(o: HistoryTickerDB): TickerDomain = TickerDomain(
        ticker = o.ticker,
        timestamp = o.timestamp
    )

    override fun reverseTransform(o: TickerDomain): HistoryTickerDB = HistoryTickerDB(
        ticker = o.ticker,
        timestamp = o.timestamp
    )
}