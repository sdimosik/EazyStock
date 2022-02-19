package com.sdimosikvip.data.sources

import com.sdimosikvip.domain.Outcome
import com.sdimosikvip.domain.ServiceUnavailableException
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
                    return Outcome.Success(
                        mapper.transform(body)
                    )
                }
            }
            // TODO handle http code exception
            return Outcome.Failure(ServiceUnavailableException())
        } catch (e: Exception) {
            return Outcome.Failure(e)
        }
    }
}