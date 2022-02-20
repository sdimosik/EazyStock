package com.sdimosikvip.eazystock.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.sdimosikvip.eazystock.R
import com.sdimosikvip.eazystock.base.BaseFragment
import com.sdimosikvip.eazystock.databinding.FragmentHomeBinding
import com.sdimosikvip.eazystock.ui.favourite.FavouriteFragment
import com.sdimosikvip.eazystock.ui.stocks.StocksFragment

private val screens = listOf(
    StocksFragment::class.java,
    FavouriteFragment::class.java
)

private val screensTittle = listOf(
    R.string.tittle_stocks,
    R.string.tittle_favourite
)

const val COUNT_VIEWPAGER_FRAGMENT = 2

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