package com.sdimosikvip.data.network.finnhub.mapper

import com.sdimosikvip.data.network.finnhub.models.ResultResponse
import com.sdimosikvip.data.network.finnhub.models.SymbolLookupResponse
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.ResultDomain
import com.sdimosikvip.domain.models.SymbolLookupDomain
import javax.inject.Inject

class SymbolLookupResponseMapper @Inject constructor(
    private val resultResponseMapper: BaseMapper<ResultResponse, ResultDomain>
) : BaseMapper<SymbolLookupResponse, SymbolLookupDomain> {
    override fun transform(o: SymbolLookupResponse): SymbolLookupDomain = SymbolLookupDomain(
        count = o.count,
        result = o.result.map { resultResponseMapper.transform(it) }
    )

    override fun reverseTransform(o: SymbolLookupDomain): SymbolLookupResponse =
        SymbolLookupResponse(
            count = o.count,
            result = o.result.map { resultResponseMapper.reverseTransform(it) }
        )
}