package com.example.mycryptoapp.data.workers

import android.content.Context
import androidx.work.*
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
        val fSyms =
            inputData.getString(KEY_NAMES_LIST) ?: throw RuntimeException("fSyms == null")
        while (true) {
            try {
                val jsonContainer =
                    apiService.getFullPriceList(fSyms = fSyms) // получаем полный прайслист из сети
                val coinInfoDtoList =
                    mapper.mapJsonContainerToListCoinInfo(jsonContainer) // json -> Dto
                val dbModelList =
                    coinInfoDtoList.map { mapper.mapDtoToDbModel(it) } // Dto -> DbModel
                coinInfoDao.insertPriceList(dbModelList) // передаем в БД}
            } catch (e: Exception) {
            }
            delay(10_000)
        }
    }

    companion object {
        const val NAME = "RefreshDataWorker"
        private const val KEY_NAMES_LIST = "key_names_list"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
        }
    }
}