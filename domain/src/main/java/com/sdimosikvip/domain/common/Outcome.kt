package com.sdimosikvip.domain.common

data class Outcome<out T>(
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
        fun <T> success(data: T): Outcome<T> {
            return Outcome(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): Outcome<T> {
            return Outcome(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): Outcome<T> {
            return Outcome(Status.LOADING, data, null)
        }
    }
}
