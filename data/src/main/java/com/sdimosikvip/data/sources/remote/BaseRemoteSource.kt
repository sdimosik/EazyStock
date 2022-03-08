package com.sdimosikvip.data.sources.remote

import com.sdimosikvip.data.network.ConnectionManager
import com.sdimosikvip.domain.*
import com.sdimosikvip.domain.mapper.BaseMapper
import retrofit2.Response
import timber.log.Timber

abstract class BaseRemoteSource {

    companion object {
        const val TAG = "BaseRemoteSource"
    }

    protected suspend fun <R, D> getResult(
        connectionManager: ConnectionManager,
        mapper: BaseMapper<R, D>,
        call: suspend () -> Response<R>,
    ): Outcome<D> {
        try {
            val isConnected = connectionManager.isConnected()

            val response = call()
            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    return Outcome.Success(
                        mapper.transform(body)
                    )
                }
            }

            if (!isConnected) {
                return Outcome.Failure(NoConnectionException())
            }

            return when (response.code()) {
                in 400..499 -> Outcome.Failure(ClientException())
                in 500..599 -> Outcome.Failure(ServerException())
                else -> Outcome.Failure(UncheckedException())
            }
        } catch (e: Exception) {
            return Outcome.Failure(e)
        }
    }

    protected suspend fun <R, D> getNullableResult(
        connectionManager: ConnectionManager,
        mapper: BaseMapper<R, D>,
        call: suspend () -> Response<R>,
    ): Outcome<D>? {
        try {
            val isConnected = connectionManager.isConnected()

            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                Timber.tag(TAG).d("$body")

                if (body != null) {
                    return Outcome.Success(
                        mapper.transform(body)
                    )
                } else {
                    return null
                }
            }

            if (!isConnected) {
                return null//Outcome.Failure(NoConnectionException())
            }

            Timber.tag(TAG).e("exception code: ${response.code()}")
            return when (response.code()) {
                in 400..499 -> null //Outcome.Failure(ClientException())
                in 500..599 -> null //Outcome.Failure(ServerException())
                else -> null //Outcome.Failure(UncheckedException())
            }
        } catch (e: Exception) {
            return Outcome.Failure(e)
        }
    }
}