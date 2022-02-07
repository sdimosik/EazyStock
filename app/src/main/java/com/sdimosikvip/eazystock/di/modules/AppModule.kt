package com.sdimosikvip.eazystock.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class AppModule {

    @Provides
    fun provideContext(application: Application): Context = application.applicationContext
}