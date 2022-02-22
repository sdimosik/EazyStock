package com.sdimosikvip.eazystock.ui.stocks

import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.sdimosikvip.eazystock.R
import com.sdimosikvip.eazystock.base.BaseFragment
import com.sdimosikvip.eazystock.base.BaseViewModel
import com.sdimosikvip.eazystock.databinding.FragmentStocksBinding
import com.sdimosikvip.eazystock.ui.adapters.AsyncListDifferAdapter
import com.sdimosikvip.eazystock.ui.adapters.delegates.StocksDelegates
import com.sdimosikvip.eazystock.utils.setup


class StocksFragment() : BaseFragment(
    tittleRes = R.string.stocks_fragment_name,
    layoutId = R.layout.fragment_stocks
), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val TITTLE_ID = R.string.stocks_fragment_name
    }

    override val binding by viewBinding(FragmentStocksBinding::bind)
    private val stocksViewModel: StocksViewModel by viewModels {
        viewModelFactory
    }

    private val glide: RequestManager by lazy {
        Glide.with(this)
    }

    private val adapter: AsyncListDifferAdapter by lazy {
        AsyncListDifferAdapter(
            AdapterDelegatesManager(StocksDelegates.lightAndDarkAdapterDelegate(
                glide,
                { stocksViewModel.addFavouriteStock(it.ticker) },
                { stocksViewModel.deleteFavouriteStock(it.ticker) }
            ))
        )
    }

    override fun setupViews() {
        super.setupViews()

        with(binding) {
            shimmerRecyclerView.setup(adapter, R.layout.shimmer_item_stock)
            swipeRefreshLayout.setOnRefreshListener(this@StocksFragment)
        }
    }

    override fun onRefresh() {
        if (stocksViewModel.state.value == BaseViewModel.State.Init) {
            binding.swipeRefreshLayout.isRefreshing = false
            return
        }

        stocksViewModel.updateRecommendationStocks(false)
    }

    override fun subscribe() {
        super.subscribe()

        stocksViewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is BaseViewModel.State.Init -> {
                    binding.shimmerRecyclerView.showShimmer()
                    stocksViewModel.updateRecommendationStocks(true)
                }
                is BaseViewModel.State.IsLoading -> {
                    if (state.isLoading) {
                        binding.swipeRefreshLayout.isRefreshing = true
                    } else {
                        binding.swipeRefreshLayout.isRefreshing = false
                        binding.shimmerRecyclerView.hideShimmer()
                    }
                }
                is BaseViewModel.State.ShowToast -> {
                    showError(getString(state.messageRes))
                }
            }
        }

        stocksViewModel.stock.observe(viewLifecycleOwner) {
            adapter.items = it
        }
    }
}