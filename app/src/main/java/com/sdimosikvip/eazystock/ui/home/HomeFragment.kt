package com.sdimosikvip.eazystock.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
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


private val screens = listOf(
    RecommendationFragment::class.java,
    FavouriteFragment::class.java
)

val screensTittle = listOf(
    R.string.tittle_stocks,
    R.string.tittle_favourite
)

const val COUNT_VIEWPAGER_FRAGMENT = 2

class HomeFragment() : BaseFragment(
    tittleRes = R.string.fragment_home_name,
    layoutId = R.layout.fragment_home
), SwipeRefreshLayout.OnRefreshListener {

    override val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels {
        viewModelFactory
    }
    private val sharedViewModel: MainViewModel by activityViewModels {
        viewModelFactory
    }

    private val glide: RequestManager by lazy {
        Glide.with(this)
    }

    override fun setupViews() {
        super.setupViews()

        with(binding) {

            mainViewPager.adapter = ViewPagerAdapter(this@HomeFragment)
            TabLayoutMediator(mainTabLayout, mainViewPager) { tab, position ->
                tab.text = getString(getOrderFragmentTittleId(position))
            }.attach()

            swipeRefreshLayout.setOnRefreshListener(this@HomeFragment)

            appBar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
                override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {}
            })

            search.root.setOnClickListener {
                findNavController().navigate(R.id.action_fragment_home_to_fragment_search)
            }
        }
    }

    override fun onRefresh() {
        if (viewModel.state.value == BaseViewModel.State.Init) {
            binding.swipeRefreshLayout.isRefreshing = false
            return
        }

        viewModel.update()
    }

    override fun subscribe() {
        super.subscribe()

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is BaseViewModel.State.Init -> {

                }
                is BaseViewModel.State.IsLoading -> {
                    binding.swipeRefreshLayout.isRefreshing = state.isLoading
                }
                is BaseViewModel.State.ShowToast -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    showError(getString(state.messageRes))
                }
            }
        }

        sharedViewModel.favouriteStocksLiveData.observe(viewLifecycleOwner) {
            viewModel.fetch(it)
        }
    }
}

private fun isValidPosition(position: Int): Boolean {
    return position >= 0 && position < screens.size
}

private fun getOrderFragment(position: Int): BaseFragment {
    if (!isValidPosition(position)) {
        throw IllegalArgumentException("Illegal position: $position")
    }
    return screens[position].newInstance() as BaseFragment
}

private fun getOrderFragmentTittleId(position: Int): Int {
    if (!isValidPosition(position)) {
        throw IllegalArgumentException("Illegal position: $position")
    }
    return screensTittle[position]
}

private class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = COUNT_VIEWPAGER_FRAGMENT

    override fun createFragment(position: Int): Fragment {
        return getOrderFragment(position)
    }
}