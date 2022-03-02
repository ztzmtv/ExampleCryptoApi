package com.example.mycryptoapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mycryptoapp.domain.GetCoinInfoListUseCase
import com.example.mycryptoapp.domain.GetCoinInfoUseCase
import com.example.mycryptoapp.domain.LoadDataUseCase
import javax.inject.Inject

class CoinViewModel @Inject constructor(
    private val getCoinInfoListUseCase: GetCoinInfoListUseCase,
    private val getCoinInfoUseCase: GetCoinInfoUseCase,
    private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {

    val coinInfoList = getCoinInfoListUseCase()

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)

    init {
        log("init")
        loadDataUseCase()
        log("init - done")
    }

    companion object {
        private const val TAG = "CoinViewModel_TAG"

        private fun log(string: String) {
            Log.d(TAG, string)
        }
    }
}