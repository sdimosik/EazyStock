package com.sdimosikvip.eazystock.di.modules

import com.sdimosikvip.eazystock.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainActivityModule {

    @ContributesAndroidInjector(
        modules = [
            FragmentBuildersModule::class
        ]
    )

    fun contributeMainActivity(): MainActivity
}