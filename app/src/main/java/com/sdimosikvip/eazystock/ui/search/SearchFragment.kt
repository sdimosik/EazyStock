package com.sdimosikvip.eazystock.ui.search

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.RequestManager
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.sdimosikvip.eazystock.R
import com.sdimosikvip.eazystock.base.BaseFragment
import com.sdimosikvip.eazystock.base.BaseViewModel
import com.sdimosikvip.eazystock.databinding.FragmentSearchBinding
import com.sdimosikvip.eazystock.ui.MainActivity
import com.sdimosikvip.eazystock.ui.MainViewModel
import com.sdimosikvip.eazystock.ui.adapters.AsyncListDifferAdapter
import com.sdimosikvip.eazystock.ui.adapters.delegates.MainDelegates
import com.sdimosikvip.eazystock.ui.detail.DetailFragment
import com.sdimosikvip.eazystock.ui.home.favourite_stocks.FavouriteFragment
import com.sdimosikvip.eazystock.utils.afterTextChangedDebounce
import timber.log.Timber
import javax.inject.Inject


class SearchFragment() : BaseFragment(
    tittleRes = R.string.fragment_search_name,
    layoutId = R.layout.fragment_search,
) {

    companion object {
        const val TAG = "SearchFragment"
        private const val BASE = "com.sdimosikvip.eazystock.ui.search"
        const val STOCK_UI = "$BASE STOCK_UI"
    }

    override val binding by viewBinding(FragmentSearchBinding::bind)
    private val searchViewModel: SearchViewModel by viewModels {
        viewModelFactory
    }

    @Inject
    lateinit var glide: RequestManager

    private val sharedViewModel: MainViewModel by activityViewModels {
        viewModelFactory
    }

    private val adapter_popular_ticker: AsyncListDifferAdapter by lazy {
        AsyncListDifferAdapter(
            AdapterDelegatesManager(MainDelegates.tickerDelegate {
                binding.frgSearchSearch.input.setText(it.ticker, TextView.BufferType.EDITABLE)
                binding.frgSearchSearch.input.setSelection(it.ticker.length)
            })
        )
    }

    private val adapter_history: AsyncListDifferAdapter by lazy {
        AsyncListDifferAdapter(
            AdapterDelegatesManager(MainDelegates.tickerDelegate {
                binding.frgSearchSearch.input.setText(it.ticker, TextView.BufferType.EDITABLE)
                binding.frgSearchSearch.input.setSelection(it.ticker.length)
            })
        )
    }

    private val adapter_stocks_result: AsyncListDifferAdapter by lazy {
        AsyncListDifferAdapter(
            AdapterDelegatesManager(MainDelegates.stockLightAndDarkAdapterDelegate(
                glide,
                { sharedViewModel.addFavouriteStock(it) },
                { sharedViewModel.deleteFavouriteStock(it) },
                { stockUI, itemStockBinding ->
                    val bundle = bundleOf(DetailFragment.STOCK_UI to stockUI)
                    findNavController().navigate(
                        R.id.action_fragment_search_to_fragment_detail,
                        bundle
                    )
                }
            ))
        )
    }

    override fun setupViews() {
        super.setupViews()

        with(binding.frgSearchSearch) {

            changeVisibleResultContent(adapter_stocks_result.items.isEmpty())

            backImage.setOnClickListener {
                findNavController().navigateUp()
            }
            crossImage.setOnClickListener {
                input.text = null
            }

            (requireActivity() as MainActivity).showSoftKeyboard(input)

            input.afterTextChangedDebounce(600) {
                if (view == null) return@afterTextChangedDebounce

                if (it.isEmpty()) {
                    changeVisibleResultContent(true)
                    return@afterTextChangedDebounce
                }

                changeVisibleResultContent(false)
                searchViewModel.searchJob?.cancel()
                searchViewModel.search(it)
            }
        }

        with(binding) {
            popularRequest.headerTitle.text = getString(R.string.popular_request_tittle)
            popularRequest.recyclerview.adapter = adapter_popular_ticker

            historySearch.headerTitle.text = getString(R.string.history_search_tittle)
            historySearch.recyclerview.adapter = adapter_history

            resultStocks.headerTitle.text = getString(R.string.stocks_result_tittle)
            resultStocks.recyclerview.adapter = adapter_stocks_result
        }
    }

    override fun subscribe() {
        super.subscribe()

        searchViewModel.popularTickers.observe(viewLifecycleOwner) {
            adapter_popular_ticker.items = it
        }
        searchViewModel.historySearch.observe(viewLifecycleOwner) {
            adapter_history.items = it
            binding.historySearch.recyclerview.layoutManager?.scrollToPosition(0)
        }

        lifecycleScope.launchWhenStarted {
            searchViewModel.res.collect {
                adapter_stocks_result.items = it
            }
        }

        searchViewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is BaseViewModel.State.Init -> {
                    binding.progressBar.visibility = View.INVISIBLE
                }
                is BaseViewModel.State.IsLoading -> {
                    if (state.isLoading) {
                        binding.progressBar.visibility = View.VISIBLE
                    } else {
                        binding.progressBar.visibility = View.INVISIBLE
                    }
                }
                is BaseViewModel.State.ShowToast -> {

                }
            }
        }
    }

    private fun changeVisibleResultContent(isEmptySearch: Boolean) {
        Timber.tag(TAG).i("changeVisibleResultContent, search result is empty: $isEmptySearch")
        with(binding) {
            if (isEmptySearch) {
                startContainer.visibility = View.VISIBLE
                resultContainer.visibility = View.GONE
                frgSearchSearch.crossImage.visibility = View.GONE
            } else {
                startContainer.visibility = View.GONE
                resultContainer.visibility = View.VISIBLE
                frgSearchSearch.crossImage.visibility = View.VISIBLE
            }
        }
    }
}