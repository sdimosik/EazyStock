package com.sdimosikvip.data.sources

import com.sdimosikvip.domain.common.Outcome
import com.sdimosikvip.domain.mapper.BaseMapper
import retrofit2.Response

abstract class BaseRemoteSource {

    protected suspend fun <R, D> getResult(
        mapper: BaseMapper<R, D>,
        call: suspend () -> Response<R>
    ): Outcome<D> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return Outcome.success(
                        mapper.transformToDomain(body)
                    )
                }
            }
            return errorNetwork(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return errorNetwork(e.message ?: e.toString())
        }
    }

    private fun <D> errorNetwork(message: String): Outcome<D> {
        return Outcome.error(
            "Network call has failed for a following reason: $message"
        )
    }
}