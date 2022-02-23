package com.sdimosikvip.eazystock.ui.favourite

import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.sdimosikvip.eazystock.R
import com.sdimosikvip.eazystock.base.BaseFragment
import com.sdimosikvip.eazystock.base.BaseViewModel
import com.sdimosikvip.eazystock.databinding.FragmentFavouriteBinding
import com.sdimosikvip.eazystock.ui.adapters.AsyncListDifferAdapter
import com.sdimosikvip.eazystock.ui.adapters.delegates.StocksDelegates
import com.sdimosikvip.eazystock.utils.setup


class FavouriteFragment() : BaseFragment(
    tittleRes = R.string.favourite_fragment_name,
    layoutId = R.layout.fragment_favourite
), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val TITTLE_ID = R.string.favourite_fragment_name
    }

    override val binding by viewBinding(FragmentFavouriteBinding::bind)
    private val favouriteViewModel: FavouriteViewModel by viewModels {
        viewModelFactory
    }

    private val glide: RequestManager by lazy {
        Glide.with(this)
    }

    private val adapter: AsyncListDifferAdapter by lazy {
        AsyncListDifferAdapter(
            AdapterDelegatesManager(
                StocksDelegates.lightAndDarkAdapterDelegate(glide,
                    { favouriteViewModel.addFavouriteStock(it) },
                    { favouriteViewModel.deleteFavouriteStock(it) })
            )
        )
    }

    override fun setupViews() {
        super.setupViews()

        with(binding) {
            shimmerRecyclerView.setup(adapter, R.layout.shimmer_item_stock)
            swipeRefreshLayout.setOnRefreshListener(this@FavouriteFragment)
        }
    }

    override fun onRefresh() {
        if (favouriteViewModel.state.value == BaseViewModel.State.Init) {
            binding.swipeRefreshLayout.isRefreshing = false
            return
        }

        favouriteViewModel.getFavouriteStocks(false)
    }

    override fun subscribe() {
        super.subscribe()

        favouriteViewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is BaseViewModel.State.Init -> {
                    binding.shimmerRecyclerView.showShimmer()
                    favouriteViewModel.getFavouriteStocks(true)
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

        favouriteViewModel.stock.observe(viewLifecycleOwner) {
            adapter.items = it
        }
    }
}