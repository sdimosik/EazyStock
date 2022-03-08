package com.sdimosikvip.eazystock.ui.home.recommendation_stocks

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.sdimosikvip.eazystock.R
import com.sdimosikvip.eazystock.base.BaseFragment
import com.sdimosikvip.eazystock.base.BaseViewModel
import com.sdimosikvip.eazystock.databinding.FragmentRecommendationStocksBinding
import com.sdimosikvip.eazystock.ui.MainViewModel
import com.sdimosikvip.eazystock.ui.adapters.AsyncListDifferAdapter
import com.sdimosikvip.eazystock.ui.adapters.delegates.MainDelegates
import com.sdimosikvip.eazystock.ui.home.HomeViewModel
import com.sdimosikvip.eazystock.utils.setup
import timber.log.Timber


class RecommendationFragment() : BaseFragment(
    tittleRes = R.string.recommendation_stocks_fragment_name,
    layoutId = R.layout.fragment_recommendation_stocks
) {

    companion object {
        const val TITTLE_ID = R.string.recommendation_stocks_fragment_name
        const val TAG_FRAGMENT = "recommendation_fragment"
    }

    override val binding by viewBinding(FragmentRecommendationStocksBinding::bind)

    private val homeViewModel: HomeViewModel by viewModels(
        ownerProducer = { requireParentFragment() },
        factoryProducer = { viewModelFactory }
    )

    private val sharedViewModel: MainViewModel by activityViewModels {
        viewModelFactory
    }

    private val glide: RequestManager by lazy {
        Glide.with(this)
    }

    private val adapter: AsyncListDifferAdapter by lazy {
        AsyncListDifferAdapter(
            AdapterDelegatesManager(MainDelegates.stockLightAndDarkAdapterDelegate(
                glide,
                { sharedViewModel.addFavouriteStock(it) },
                { sharedViewModel.deleteFavouriteStock(it) },
                { findNavController().navigate(R.id.action_fragment_home_to_fragment_detail) }
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
                    Timber.tag(TAG_FRAGMENT).i("state: init")
                    binding.shimmerRecyclerView.showShimmer()
                }
                is BaseViewModel.State.IsLoading -> {
                    Timber.tag(TAG_FRAGMENT).i("state: loading ${state.isLoading}")
                    if (!state.isLoading) binding.shimmerRecyclerView.hideShimmer()
                }
                is BaseViewModel.State.ShowToast -> {
                    Timber.tag(TAG_FRAGMENT).i("state: error")
                    binding.shimmerRecyclerView.hideShimmer()
                }
            }
        }

        homeViewModel.stockTop.observe(viewLifecycleOwner) {
            adapter.items = it
        }
    }
}