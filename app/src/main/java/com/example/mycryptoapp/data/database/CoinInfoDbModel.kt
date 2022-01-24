package com.example.mycryptoapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "full_price_list")
data class CoinInfoDbModel(
    @PrimaryKey
    val fromsymbol: String,
    val tosymbol: String?,
    val price: Double?,
    val lastupdate: Long?,
    val highday: Int?,
    val lowday: Double?,
    val lastmarket: String?,
    val imageurl: String?,
)