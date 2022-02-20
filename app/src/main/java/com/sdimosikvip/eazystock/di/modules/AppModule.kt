package com.sdimosikvip.eazystock.di.modules

import android.app.Application
import android.content.Context
import com.sdimosikvip.data.network.ConnectionManager
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class AppModule {

    @Provides
    @Named("application.context")
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    fun provideConnectionManager(context: Context): ConnectionManager =
        ConnectionManager(context)
}