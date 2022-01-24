package com.example.mycryptoapp.domain

data class CoinInfo(
    val fromsymbol: String,
    val tosymbol: String?,
    val price: Double?,
    val lastupdate: Long?,
    val highday: Int?,
    val lowday: Double?,
    val lastmarket: String?,
    val imageurl: String?,
)