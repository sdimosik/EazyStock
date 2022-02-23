package com.sdimosikvip.data.network.mboum.mapper

import com.sdimosikvip.data.network.mboum.models.MostWatchedTicketResponse
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.TickersDomain

class TickersMapper : BaseMapper<MostWatchedTicketResponse, TickersDomain> {
    override fun transform(type: MostWatchedTicketResponse): TickersDomain =
        TickersDomain(
            listTickers = type.data[0].quotes
        )
}