package com.example.mycryptoapp.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.mycryptoapp.data.database.AppDatabase
import com.example.mycryptoapp.data.mapper.CoinMapper
import com.example.mycryptoapp.data.network.ApiFactory
import com.example.mycryptoapp.data.workers.RefreshDataWorker
import com.example.mycryptoapp.domain.CoinInfo
import com.example.mycryptoapp.domain.CoinRepository

class CoinRepositoryImpl(
    private val application: Application
) : CoinRepository {

    private val coinInfoDao = AppDatabase.getInstance(application).coinPriceInfoDao()

    private val mapper = CoinMapper()

    private val apiService = ApiFactory.apiService

    override fun getCoinInfo(fSym: String): LiveData<CoinInfo> {
        return Transformations.map(coinInfoDao.getPriceInfoAboutCoin(fSym)) {
            mapper.mapDbModelToEntity(it)
        }
    }

    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return Transformations.map(coinInfoDao.getPriceList()) {
            it.map {
                mapper.mapDbModelToEntity(it)
            }
        }
    }

    override fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            RefreshDataWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            RefreshDataWorker.makeRequest()
        )
    }

    companion object {
        private const val TAG = "CoinRepoImpl_TAG"

        private fun log(string: String) {
            Log.d(TAG, string)
        }
    }

}