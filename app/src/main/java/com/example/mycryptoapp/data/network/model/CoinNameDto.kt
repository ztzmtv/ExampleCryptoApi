package com.example.mycryptoapp.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinNameDto(
    @SerializedName("Name")
    @Expose
    var name: String? = null,
)
