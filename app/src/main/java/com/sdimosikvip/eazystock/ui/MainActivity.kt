package com.sdimosikvip.eazystock.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.sdimosikvip.eazystock.R
import com.sdimosikvip.eazystock.base.BaseFragment
import com.sdimosikvip.eazystock.databinding.ActivityMainBinding
import com.sdimosikvip.eazystock.ui.MainActivity.Companion.COUNT_VIEWPAGER_FRAGMENT
import com.sdimosikvip.eazystock.ui.favourite.FavouriteFragment
import com.sdimosikvip.eazystock.ui.stocks.StocksFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity() : AppCompatActivity(R.layout.activity_main) {

    companion object {
        const val MAIN_RESULT = "com.sdimosikvip.eazystock.ui.MAIN_RESULT_KEY"

        const val COUNT_VIEWPAGER_FRAGMENT = 2
    }

    private val binding by viewBinding(ActivityMainBinding::bind)
    private lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = getColor(R.color.colorPrimaryVariant)

        /* val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)*/

        adapter = ViewPagerAdapter(this)
        binding.mainViewPager.adapter = adapter

        TabLayoutMediator(binding.mainTabLayout, binding.mainViewPager) { tab, position ->
            tab.text = getString(getOrderFragmentTittleId(position))
        }.attach()

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

class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = COUNT_VIEWPAGER_FRAGMENT

    override fun createFragment(position: Int): Fragment {
        return getOrderFragment(position)
    }
}