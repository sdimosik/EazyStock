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
import com.sdimosikvip.eazystock.ui.home.HomeViewModel
import com.sdimosikvip.eazystock.utils.setup


class StocksFragment() : BaseFragment(
    tittleRes = R.string.stocks_fragment_name,
    layoutId = R.layout.fragment_stocks
) {

    companion object {
        const val TITTLE_ID = R.string.stocks_fragment_name
    }

    override val binding by viewBinding(FragmentStocksBinding::bind)
    private val homeViewModel: HomeViewModel by viewModels {
        viewModelFactory
    }

    private val glide: RequestManager by lazy {
        Glide.with(this)
    }

    private val adapter: AsyncListDifferAdapter by lazy {
        AsyncListDifferAdapter(
            AdapterDelegatesManager(StocksDelegates.lightAndDarkAdapterDelegate(
                glide,
                { homeViewModel.addFavouriteStock(it) },
                { homeViewModel.deleteFavouriteStock(it) }
            ))
        )
    }

    override fun setupViews() {
        super.setupViews()

        with(binding) {
            shimmerRecyclerView.setup(adapter, R.layout.shimmer_item_stock)
        }
    }

    override fun subscribe() {
        super.subscribe()

        homeViewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is BaseViewModel.State.Init -> {
                    binding.shimmerRecyclerView.showShimmer()
                }
                is BaseViewModel.State.IsLoading -> {
                    if (!state.isLoading) binding.shimmerRecyclerView.hideShimmer()
                }
                is BaseViewModel.State.ShowToast  -> {
                     binding.shimmerRecyclerView.hideShimmer()
                }
            }
        }

        homeViewModel.stockTop.observe(viewLifecycleOwner) {
            adapter.items = it
        }
    }
}