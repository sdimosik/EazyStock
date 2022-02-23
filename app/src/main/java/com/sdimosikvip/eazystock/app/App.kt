package com.sdimosikvip.eazystock.app

import android.content.Context
import android.graphics.Typeface
import com.jakewharton.threetenabp.AndroidThreeTen
import com.sdimosikvip.eazystock.BuildConfig
import com.sdimosikvip.eazystock.di.AppComponent
import com.sdimosikvip.eazystock.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import es.dmoral.toasty.Toasty
import timber.log.Timber
import timber.log.Timber.Forest.plant


class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().application(this).build()

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)

        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }

        Toasty.Config.getInstance()
            .setToastTypeface(
                Typeface.createFromAsset(
                    this.assets,
                    "montserrat_semi_bold.ttf"
                )
            )
            .setTextSize(14)
            .apply()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> applicationContext.appComponent
    }
