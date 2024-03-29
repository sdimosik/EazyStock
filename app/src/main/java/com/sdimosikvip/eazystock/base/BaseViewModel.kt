package com.sdimosikvip.eazystock.base

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sdimosikvip.domain.NoConnectionException
import com.sdimosikvip.eazystock.R
import kotlinx.coroutines.CoroutineExceptionHandler
import timber.log.Timber

abstract class BaseViewModel() : ViewModel() {

    companion object {
        const val TAG = "BaseViewModel"
    }

    private val _state = MutableLiveData<State>(State.Init)
    val state: LiveData<State> = _state

    protected val handlerException = CoroutineExceptionHandler { _, exception ->
        run {
            Timber.tag(TAG).e(exception)
            val messageRes = getMessageExceptionRes(exception)
            setToastMessage(messageRes)
            hideLoading()
        }
    }

    protected fun setLoading() {
        _state.value = State.IsLoading(true)
    }

    protected fun hideLoading() {
        _state.value = State.IsLoading(false)
    }

    protected fun setToastMessage(@StringRes messageRes: Int) {
        _state.value = State.ShowToast(messageRes)
    }

    protected fun getMessageExceptionRes(cause: Throwable): Int {
        return when (cause) {
            is NoConnectionException -> R.string.network_no_connection_error_message
            else -> R.string.generic_error_message
        }
    }

    sealed class State {
        object Init : State()
        data class IsLoading(val isLoading: Boolean) : State()
        data class ShowToast(@StringRes val messageRes: Int) : State()
    }
}

