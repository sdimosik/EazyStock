package com.sdimosikvip.eazystock.ui

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sdimosikvip.eazystock.R
import com.sdimosikvip.eazystock.databinding.ActivityMainBinding
import com.sdimosikvip.eazystock.utils.connection.ConnectionLiveData
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber


class MainActivity() : DaggerAppCompatActivity(R.layout.activity_main) {

    companion object {
        const val MAIN_RESULT = "com.sdimosikvip.eazystock.ui.MAIN_RESULT_KEY"
    }

    private val binding by viewBinding(ActivityMainBinding::bind)
    private lateinit var navController: NavController

    private val connectionLiveData: ConnectionLiveData by lazy {
        ConnectionLiveData(application)
    }

    override fun onStart() {
        super.onStart()
        connectionLiveData.observe(this) { isConnected ->
            if (isConnected) {
                binding.internetConnectionInfo.visibility = View.GONE
            } else {
                binding.internetConnectionInfo.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        connectionLiveData.removeObservers(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeColorOnStatusBar()

        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.navController
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun changeColorOnStatusBar() {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = getColor(R.color.colorPrimaryVariant)
    }

    fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun hideKeyboard(v: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(v.applicationWindowToken, 0)
    }

    override fun finish() {
        Timber.tag("mainactivity").i("finish")
        super.finish()
    }
}