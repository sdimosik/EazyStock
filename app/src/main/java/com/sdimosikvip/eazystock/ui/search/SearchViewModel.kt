package com.sdimosikvip.eazystock.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sdimosikvip.domain.interactor.FavouriteStockInteractor
import com.sdimosikvip.domain.interactor.HistorySearchInteractor
import com.sdimosikvip.domain.interactor.RecommendationStockInteractor
import com.sdimosikvip.domain.models.FavouriteTickerDomain
import com.sdimosikvip.domain.models.TickerDomain
import com.sdimosikvip.eazystock.base.BaseViewModel
import com.sdimosikvip.eazystock.mapper.stockDomainToUI
import com.sdimosikvip.eazystock.model.StockUI
import com.sdimosikvip.eazystock.model.TickerUI
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val favouriteStockInteractor: FavouriteStockInteractor,
    private val recommendationStockInteractor: RecommendationStockInteractor,
    private val historySearchInteractor: HistorySearchInteractor
) : BaseViewModel() {

    companion object {
        const val TAG = "SearchViewModel"
    }

    private val _popularTickers = MutableLiveData<List<TickerUI>>(
        listOf(
            TickerUI("FB"),
            TickerUI("AAPL"),
            TickerUI("AMZN"),
            TickerUI("NFLX"),
            TickerUI("GOOGL"),
            TickerUI("TSLA"),
            TickerUI("COKE"),
            TickerUI("EBAY"),
            TickerUI("KOD"),
        )
    )
    val popularTickers: LiveData<List<TickerUI>> = _popularTickers

    val historySearch: LiveData<List<TickerUI>> =
        historySearchInteractor.history().map { it.map { TickerUI(it.ticker) } }.asLiveData()

    private val _stockResult = MutableLiveData<List<StockUI>>(listOf())
    val stockResult: LiveData<List<StockUI>> = _stockResult

    private val _res = MutableSharedFlow<List<StockUI>>(replay = 1)
    val res = _res.asSharedFlow()

    val countItemResult = 3

    fun search(text: String) {
        viewModelScope.launch(handlerException) {

            historySearchInteractor.saveQueryHistory(TickerDomain(text, System.currentTimeMillis()))

            historySearchInteractor.search(text)
                .onStart { setLoading() }
                .onCompletion { hideLoading() }
                .map { it.map { item -> stockDomainToUI(item) } }
                .collect {
                    Timber.tag(TAG).d("search list size: ${it.size}")
                    Timber.tag(TAG).d("search list: $it")
                    _res.tryEmit(it)
                }
        }
    }
}