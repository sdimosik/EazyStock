package com.sdimosikvip.eazystock.app

import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import com.sdimosikvip.eazystock.di.AppComponent
import com.sdimosikvip.eazystock.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().application(this).build()

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> applicationContext.appComponent
    }
