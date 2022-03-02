package com.example.mycryptoapp.presentation

import android.app.Application
import androidx.work.Configuration
import com.example.mycryptoapp.data.database.AppDatabase
import com.example.mycryptoapp.data.mapper.CoinMapper
import com.example.mycryptoapp.data.network.ApiFactory
import com.example.mycryptoapp.data.workers.RefreshDataWorkerFactory
import com.example.mycryptoapp.di.DaggerApplicationComponent
import javax.inject.Inject

class CoinApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: RefreshDataWorkerFactory

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }


    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }
}