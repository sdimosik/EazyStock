package com.sdimosikvip.eazystock.di

import android.app.Application
import com.sdimosikvip.eazystock.app.App
import com.sdimosikvip.eazystock.di.modules.AppModule
import com.sdimosikvip.eazystock.di.modules.DataModule
import com.sdimosikvip.eazystock.di.modules.domain.DomainModule
import com.sdimosikvip.eazystock.di.modules.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        MainActivityModule::class,
        DomainModule::class,
        DataModule::class
    ]
)
    interface AppComponent : AndroidInjector<App> {


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    override fun inject(app: App)
}