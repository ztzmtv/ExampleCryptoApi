package com.example.mycryptoapp.di

import android.app.Application
import com.example.mycryptoapp.presentation.CoinApp
import com.example.mycryptoapp.presentation.CoinDetailFragment
import com.example.mycryptoapp.presentation.CoinPriceListActivity
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: CoinPriceListActivity)

    fun inject(activity: CoinDetailFragment)

    fun inject(application: CoinApp)

    @Component.Factory
    interface Factory {

        fun create(

            @BindsInstance application: Application

        ): ApplicationComponent
    }
}