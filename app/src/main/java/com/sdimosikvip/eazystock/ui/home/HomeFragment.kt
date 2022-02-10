package com.sdimosikvip.eazystock.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.sdimosikvip.eazystock.R
import com.sdimosikvip.eazystock.base.BaseFragment
import com.sdimosikvip.eazystock.databinding.FragmentHomeBinding
import com.sdimosikvip.eazystock.ui.MainActivity
import com.sdimosikvip.eazystock.ui.favourite.FavouriteFragment
import com.sdimosikvip.eazystock.ui.stocks.StocksFragment


class HomeFragment() : BaseFragment(
    tittleRes = R.string.fragment_home_name,
    layoutId = R.layout.fragment_home
) {

    override val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels {
        viewModelFactory
    }
    private val adapter by lazy {
        ViewPagerAdapter(this)
    }

    override fun setupViews() {
        super.setupViews()

        with(binding) {
            mainViewPager.adapter = adapter

            TabLayoutMediator(mainTabLayout, mainViewPager) { tab, position ->
                tab.text = getString(getOrderFragmentTittleId(position))
            }.attach()
        }
    }
}

fun getOrderFragment(position: Int): BaseFragment {
    return when (position) {
        0 -> StocksFragment()
        1 -> FavouriteFragment()
        else -> throw IllegalArgumentException("Illegal position: $position")
    }
}

fun getOrderFragmentTittleId(position: Int): Int {
    return when (position) {
        0 -> R.string.stocks_fragment_name
        1 -> R.string.favourite_fragment_name
        else -> throw IllegalArgumentException("Illegal position: $position")
    }
}

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = MainActivity.COUNT_VIEWPAGER_FRAGMENT

    override fun createFragment(position: Int): Fragment {
        return getOrderFragment(position)
    }
}