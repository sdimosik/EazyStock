package com.sdimosikvip.eazystock.ui.search

import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
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
import com.sdimosikvip.eazystock.utils.afterTextChangedDebounce
import javax.inject.Inject


class SearchFragment() : BaseFragment(
    tittleRes = R.string.fragment_search_name,
    layoutId = R.layout.fragment_search,
) {

    companion object {
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
                binding.search.input.setText(it.ticker, TextView.BufferType.EDITABLE)
                binding.search.input.setSelection(it.ticker.length)
            })
        )
    }

    private val adapter_history: AsyncListDifferAdapter by lazy {
        AsyncListDifferAdapter(
            AdapterDelegatesManager(MainDelegates.tickerDelegate {
                binding.search.input.setText(it.ticker, TextView.BufferType.EDITABLE)
                binding.search.input.setSelection(it.ticker.length)
            })
        )
    }

    private val adapter_stocks_result: AsyncListDifferAdapter by lazy {
        AsyncListDifferAdapter(
            AdapterDelegatesManager(MainDelegates.stockLightAndDarkAdapterDelegate(
                glide,
                { sharedViewModel.addFavouriteStock(it) },
                { sharedViewModel.deleteFavouriteStock(it) },
                {
                    val bundle = bundleOf(DetailFragment.STOCK_UI to it)
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

        with(binding.search) {
            backImage.setOnClickListener {
                findNavController().navigateUp()
            }
            crossImage.setOnClickListener {
                input.text = null
            }

            (requireActivity() as MainActivity).showSoftKeyboard(input)

            input.afterTextChangedDebounce(800) {
                if (view == null) return@afterTextChangedDebounce

                if (it.isEmpty()) {
                    changeVisibleResultContent(true)
                    return@afterTextChangedDebounce
                }

                changeVisibleResultContent(false)
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

        /* searchViewModel.stockResult.observe(viewLifecycleOwner) {
             adapter_stocks_result.items = it.subList(
                 0,
                 if (it.size < searchViewModel.countItemResult) it.size else searchViewModel.countItemResult
             )
             binding.resultStocks.showMore.visibility = View.VISIBLE
         }*/

        /*sharedViewModel.favouriteStocksLiveData.observe(viewLifecycleOwner) {
            searchViewModel.fetch(it)
        }*/

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
        with(binding) {
            if (isEmptySearch) {
                startContainer.visibility = View.VISIBLE
                resultContainer.visibility = View.GONE
                search.crossImage.visibility = View.GONE

                val list = searchViewModel.stockResult.value ?: return@with

                adapter_stocks_result.items = list.subList(
                    0,
                    if (list.size < searchViewModel.countItemResult) list.size else searchViewModel.countItemResult
                )
            } else {
                startContainer.visibility = View.GONE
                resultContainer.visibility = View.VISIBLE
                search.crossImage.visibility = View.VISIBLE
            }
        }
    }
}