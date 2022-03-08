package com.sdimosikvip.eazystock.ui.home.favourite_stocks

import androidx.core.os.bundleOf
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
import com.sdimosikvip.eazystock.databinding.FragmentFavouriteStocksBinding
import com.sdimosikvip.eazystock.ui.MainViewModel
import com.sdimosikvip.eazystock.ui.adapters.AsyncListDifferAdapter
import com.sdimosikvip.eazystock.ui.adapters.delegates.MainDelegates
import com.sdimosikvip.eazystock.ui.detail.DetailFragment
import com.sdimosikvip.eazystock.ui.home.HomeFragmentDirections
import com.sdimosikvip.eazystock.ui.home.HomeViewModel
import com.sdimosikvip.eazystock.utils.setup
import javax.inject.Inject


class FavouriteFragment() : BaseFragment(
    tittleRes = R.string.favourite_fragment_name,
    layoutId = R.layout.fragment_favourite_stocks
) {

    companion object {
        const val TITTLE_ID = R.string.favourite_fragment_name
        private const val BASE = "com.sdimosikvip.eazystock.ui.home.favourite_stocks"
        const val STOCK_UI = "$BASE STOCK_UI"
    }

    override val binding by viewBinding(FragmentFavouriteStocksBinding::bind)
    private val homeViewModel: HomeViewModel by viewModels(
        ownerProducer = { requireParentFragment() },
        factoryProducer = { viewModelFactory }
    )
    private val sharedViewModel: MainViewModel by activityViewModels {
        viewModelFactory
    }

    @Inject
    lateinit var glide: RequestManager

    private val adapter: AsyncListDifferAdapter by lazy {
        AsyncListDifferAdapter(
            AdapterDelegatesManager(
                MainDelegates.stockLightAndDarkAdapterDelegate(glide,
                    { sharedViewModel.addFavouriteStock(it) },
                    { sharedViewModel.deleteFavouriteStock(it) },
                    {
                        val bundle = bundleOf(DetailFragment.STOCK_UI to it)
                        findNavController().navigate(
                            R.id.action_fragment_home_to_fragment_detail,
                            bundle
                        )
                    }
                )
            )
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
                is BaseViewModel.State.ShowToast -> {
                    binding.shimmerRecyclerView.hideShimmer()
                }
            }
        }

        homeViewModel.stockFav.observe(viewLifecycleOwner) {
            adapter.items = it
        }
    }
}