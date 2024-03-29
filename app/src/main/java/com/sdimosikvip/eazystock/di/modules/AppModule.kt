package com.sdimosikvip.eazystock.di.modules

import android.app.Application
import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.sdimosikvip.data.network.ConnectionManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideConnectionManager(context: Context): ConnectionManager =
        ConnectionManager(context)

    @Provides
    @Singleton
    fun provideGlide(context: Context): RequestManager = Glide.with(context)
}