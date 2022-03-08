package com.sdimosikvip.data.network.finnhub.mapper

import com.sdimosikvip.data.network.finnhub.models.ResultResponse
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.ResultDomain

class ResultResponseMapper : BaseMapper<ResultResponse, ResultDomain> {
    override fun transform(o: ResultResponse): ResultDomain = ResultDomain(
        description = o.description,
        displaySymbol = o.displaySymbol,
        symbol = o.symbol,
        type = o.type
    )

    override fun reverseTransform(o: ResultDomain): ResultResponse = ResultResponse(
        description = o.description,
        displaySymbol = o.displaySymbol,
        symbol = o.symbol,
        type = o.type
    )
}