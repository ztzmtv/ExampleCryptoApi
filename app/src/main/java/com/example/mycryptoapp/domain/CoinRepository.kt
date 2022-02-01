package com.example.mycryptoapp.domain

import androidx.lifecycle.LiveData
import com.example.mycryptoapp.data.network.model.CoinInfoDto

interface CoinRepository {

    fun getCoinInfo(fSym: String): LiveData<CoinInfo>

    fun getCoinInfoList(): LiveData<List<CoinInfo>>

    fun loadData()

}