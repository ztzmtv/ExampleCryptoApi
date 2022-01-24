package com.example.mycryptoapp.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.mycryptoapp.data.database.AppDatabase
import com.example.mycryptoapp.data.mapper.CoinMapper
import com.example.mycryptoapp.data.network.ApiFactory
import com.example.mycryptoapp.domain.CoinInfo
import com.example.mycryptoapp.domain.CoinRepository
import kotlinx.coroutines.delay

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

    override suspend fun loadData() {
        while (true) {
            try {
                val topCoins =
                    apiService.getTopCoinsInfo(limit = 50)
                log(topCoins.toString())// загружаем список 50 валют
                val fSym =
                    mapper.mapNamesListToString(topCoins) // преобразуем в строку через запятую
                log(fSym.toString())
                val jsonContainer =
                    apiService.getFullPriceList(fSyms = fSym) // получаем полный прайслист из сети
                log(jsonContainer.toString())
                val coinInfoDtoList =
                    mapper.mapJsonContainerToListCoinInfo(jsonContainer) // json -> Dto
                log(coinInfoDtoList.toString())
                val dbModelList =
                    coinInfoDtoList.map { mapper.mapDtoToDbModel(it) } // Dto -> DbModel
                log(dbModelList.toString())
                coinInfoDao.insertPriceList(dbModelList) // передаем в БД}

            } catch (e: Exception) {
            }
            delay(10000)
        }
    }
    companion object{
        private const val TAG = "CoinRepoImpl_TAG"

        private fun log(string: String){
            Log.d(TAG, string)
        }
    }

}