package com.sdimosikvip.eazystock.ui.stocks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sdimosikvip.domain.interactor.GetStockUseCase
import com.sdimosikvip.eazystock.base.BaseViewModel
import com.sdimosikvip.eazystock.mapper.StockDomainToStockUIMapper
import com.sdimosikvip.eazystock.model.StockUI
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class StocksViewModel @Inject constructor(
    private val getStockUseCase: GetStockUseCase
) : BaseViewModel() {

    private val _stock = MutableLiveData<StockUI>()
    val stock: LiveData<StockUI> = _stock

    init {
        viewModelScope.launch(handlerException) {
            getStockUseCase.execute("AAPL")
                .onStart {
                    setLoading()
                }
                .onCompletion {
                    hideLoading()
                }
                .collect {
                    _stock.value = StockDomainToStockUIMapper().transform(it)
                }
        }
    }
}