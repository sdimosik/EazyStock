package com.sdimosikvip.eazystock.ui.home

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sdimosikvip.eazystock.R
import com.sdimosikvip.eazystock.base.AppBarStateChangeListener
import com.sdimosikvip.eazystock.base.BaseFragment
import com.sdimosikvip.eazystock.base.BaseViewModel
import com.sdimosikvip.eazystock.databinding.FragmentHomeBinding
import com.sdimosikvip.eazystock.ui.MainViewModel
import com.sdimosikvip.eazystock.ui.home.favourite_stocks.FavouriteFragment
import com.sdimosikvip.eazystock.ui.home.recommendation_stocks.RecommendationFragment
import com.sdimosikvip.eazystock.utils.ViewPagerWithTabLayoutHelper
import timber.log.Timber


private val screens = listOf(
    RecommendationFragment::class.java,
    FavouriteFragment::class.java
)

private val screensTittle = listOf(
    R.string.tittle_stocks,
    R.string.tittle_favourite
)

class HomeFragment() : BaseFragment(
    tittleRes = R.string.fragment_home_name,
    layoutId = R.layout.fragment_home
) {

    companion object {
        const val TAG = "HomeFragment"
    }

    override val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels {
        viewModelFactory
    }
    private val sharedViewModel: MainViewModel by activityViewModels {
        viewModelFactory
    }

    private val helper: ViewPagerWithTabLayoutHelper by lazy {
        ViewPagerWithTabLayoutHelper(
            this,
            screens,
            screensTittle
        )
    }

    override fun setupViews() {
        super.setupViews()

        with(binding) {

            mainViewPager.adapter = helper.getInstanceAdapter()
            TabLayoutMediator(mainTabLayout, mainViewPager) { tab, position ->
                tab.text = getString(helper.getOrderFragmentTittleId(position))
            }.attach()

            appBar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
                override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {}
            })

            frgHomeSearchContainer.setOnClickListener {
                findNavController().navigate(
                    R.id.action_fragment_home_to_fragment_search
                )
            }
        }
    }


    override fun subscribe() {
        super.subscribe()

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is BaseViewModel.State.Init -> {

                }
                is BaseViewModel.State.IsLoading -> {

                }
                is BaseViewModel.State.ShowToast -> {
                    showError(getString(state.messageRes))
                }
            }
        }

        sharedViewModel.favouriteStocksLiveData.observe(viewLifecycleOwner) {
            Timber.tag(TAG).i("shared viewmodel fetch data")
            if (it == viewModel.listFavTicker) return@observe
            viewModel.fetch(it)
        }
    }
}