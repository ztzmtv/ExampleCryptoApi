package com.example.mycryptoapp.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call

data class CoinInfoListOfData(
    @SerializedName("Data")
    @Expose
    val data: List<Datum?>
)