package com.sdimosikvip.eazystock.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sdimosikvip.domain.interactor.FavouriteStockInteractor
import com.sdimosikvip.domain.interactor.RecommendationStockInteractor
import com.sdimosikvip.domain.models.FavouriteTickerDomain
import com.sdimosikvip.eazystock.base.BaseViewModel
import com.sdimosikvip.eazystock.mapper.stockDomainToUI
import com.sdimosikvip.eazystock.model.StockUI
import com.sdimosikvip.eazystock.model.TickerUI
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val favouriteStockInteractor: FavouriteStockInteractor,
    private val recommendationStockInteractor: RecommendationStockInteractor
) : BaseViewModel() {

    private val _popularTickers = MutableLiveData<List<TickerUI>>()
    val popularTickers: LiveData<List<TickerUI>> = _popularTickers

    private val _historySearch = MutableLiveData<List<TickerUI>>()
    val historySearch: LiveData<List<TickerUI>> = _historySearch

    private val _stockResult = MutableLiveData<List<StockUI>>(listOf())
    val stockResult: LiveData<List<StockUI>> = _stockResult

    val countItemResult = 3

    init {
        _popularTickers.value = listOf(
            TickerUI("APPL"),
            TickerUI("APPLsdsd"),
            TickerUI("APPL"),
            TickerUI("APPLsd"),
            TickerUI("APPLqwe"),
            TickerUI("APPL"),
            TickerUI("adsasd"),
            TickerUI("asdasdas"),
            TickerUI("APPasdasdLwe"),
            TickerUI("bvbcvbc"),
            TickerUI("dfsd"),
            TickerUI("xczv"),
            TickerUI("sdadzxczxc"),
            TickerUI("trtew"),
        )

        _historySearch.value = listOf(
            TickerUI("Tesla"),
            TickerUI("yannnndex"),
            TickerUI("Tesla"),
            TickerUI("APPLsd"),
            TickerUI("Tesla"),
            TickerUI("Tesla"),
            TickerUI("yannnndex"),
        )
    }

    fun fetch(listFavTicker: List<FavouriteTickerDomain>) {
        viewModelScope.launch(handlerException) {
            recommendationStockInteractor.execute()
                .onStart { setLoading() }
                .onCompletion { hideLoading() }
                .map { it.map { item -> stockDomainToUI(item) } }
                .collect {
                    _stockResult.value = it
                }
        }
    }
}