package com.example.mycryptoapp.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycryptoapp.data.repository.CoinRepositoryImpl
import com.example.mycryptoapp.domain.GetCoinInfoListUseCase
import com.example.mycryptoapp.domain.GetCoinInfoUseCase
import com.example.mycryptoapp.domain.LoadDataUseCase
import kotlinx.coroutines.launch

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoinRepositoryImpl(application)

    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    val coinInfoList = getCoinInfoListUseCase()

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)

    init {
        log("init")
        viewModelScope.launch {
            loadDataUseCase()
        }
     log("init - done")
    }
    companion object{
        private const val TAG = "CoinViewModel_TAG"

        private fun log(string: String){
            Log.d(TAG, string)
        }
    }
}