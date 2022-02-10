package com.sdimosikvip.domain.common

data class ResultResponse<out T>(
    val status: Status,
    val data: T?,
    val message: String?
){

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): ResultResponse<T> {
            return ResultResponse(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): ResultResponse<T> {
            return ResultResponse(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): ResultResponse<T> {
            return ResultResponse(Status.LOADING, data, null)
        }
    }
}
