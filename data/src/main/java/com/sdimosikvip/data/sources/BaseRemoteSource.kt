package com.sdimosikvip.data.sources

import com.sdimosikvip.domain.common.ResultResponse
import com.sdimosikvip.domain.mapper.BaseMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class BaseRemoteSource {

    protected suspend fun <R, D> getResult(
        dispatcher: CoroutineDispatcher,
        mapper: BaseMapper<R, D>,
        call: suspend () -> Response<R>
    ): ResultResponse<D> {
        return withContext(dispatcher) {
            try {
                val response = call()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) return@withContext ResultResponse.success(
                        mapper.transformToDomain(
                            body
                        )
                    )
                }
                return@withContext errorNetwork(" ${response.code()} ${response.message()}")
            } catch (e: Exception) {
                return@withContext errorNetwork(e.message ?: e.toString())
            }
        }
    }

    private fun <D> errorNetwork(message: String): ResultResponse<D> {
        return ResultResponse.error(
            "Network call has failed for a following reason: $message"
        )
    }
}