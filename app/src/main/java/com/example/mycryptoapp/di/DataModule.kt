package com.example.mycryptoapp.di

import android.app.Application
import com.example.mycryptoapp.data.database.AppDatabase
import com.example.mycryptoapp.data.database.CoinInfoDao
import com.example.mycryptoapp.data.network.ApiFactory
import com.example.mycryptoapp.data.network.ApiService
import com.example.mycryptoapp.data.repository.CoinRepositoryImpl
import com.example.mycryptoapp.domain.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository

    companion object {
        @Provides
        @ApplicationScope
        fun provideCoinInfoDao(
            application: Application
        ): CoinInfoDao {
            return AppDatabase.getInstance(application).coinPriceInfoDao()
        }

        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}