package com.sdimosikvip.eazystock.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.core.view.setPadding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sdimosikvip.eazystock.R
import com.sdimosikvip.eazystock.databinding.ActivityMainBinding
import com.sdimosikvip.eazystock.utils.connection.ConnectionLiveData
import com.tapadoo.alerter.Alert
import com.tapadoo.alerter.Alerter
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity() : DaggerAppCompatActivity(R.layout.activity_main) {

    companion object {
        const val MAIN_RESULT = "com.sdimosikvip.eazystock.ui.MAIN_RESULT_KEY"
    }

    private val binding by viewBinding(ActivityMainBinding::bind)

    @Inject
    lateinit var connectionLiveData: ConnectionLiveData

    override fun onStart() {
        super.onStart()
        connectionLiveData.observe(this) { isConnected ->
            if (isConnected) {
                Alerter.clearCurrent(this)
            } else {
                showAlert()
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
    }

    @SuppressLint("ResourceAsColor")
    private fun showAlert() {
        Alerter.create(this)
            .setLayoutGravity(Gravity.BOTTOM)
            .setBackgroundColorRes(R.color.black)
            .setTitle(getString(R.string.network_no_connection_error_message))
            .enableInfiniteDuration(true)
            .setIcon(R.drawable.ic_shocked_emoji)
            .setIconColorFilter(R.color.transparent)
            .show()
    }

    private fun changeColorOnStatusBar() {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = getColor(R.color.colorPrimaryVariant)
    }
}