package com.example.mycryptoapp.domain

import androidx.lifecycle.LiveData
import com.example.mycryptoapp.data.network.model.CoinInfoDto

class GetCoinInfoUseCase(private val repository: CoinRepository) {
    //то же самое, что и invoke()
    operator fun invoke(fSym: String): LiveData<CoinInfo> {
        return repository.getCoinInfo(fSym)
    }

}