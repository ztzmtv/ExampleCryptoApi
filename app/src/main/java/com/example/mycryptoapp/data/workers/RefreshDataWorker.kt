package com.example.mycryptoapp.data.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.mycryptoapp.data.database.AppDatabase
import com.example.mycryptoapp.data.mapper.CoinMapper
import com.example.mycryptoapp.data.network.ApiFactory
import kotlinx.coroutines.delay

class RefreshDataWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    private val coinInfoDao = AppDatabase.getInstance(context).coinPriceInfoDao()

    private val mapper = CoinMapper()

    private val apiService = ApiFactory.apiService
    override suspend fun doWork(): Result {
        while (true) {
            try {
                val topCoins =
                    apiService.getTopCoinsInfo(limit = 50)
                val fSym =
                    mapper.mapNamesListToString(topCoins) // преобразуем в строку через запятую
                val jsonContainer =
                    apiService.getFullPriceList(fSyms = fSym) // получаем полный прайслист из сети
                val coinInfoDtoList =
                    mapper.mapJsonContainerToListCoinInfo(jsonContainer) // json -> Dto
                val dbModelList =
                    coinInfoDtoList.map { mapper.mapDtoToDbModel(it) } // Dto -> DbModel
                coinInfoDao.insertPriceList(dbModelList) // передаем в БД}

            } catch (e: Exception) {
            }
            delay(10000)
        }
    }

    companion object {
        const val NAME = "RefreshDataWorker"
        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>().build()

        }
    }
}