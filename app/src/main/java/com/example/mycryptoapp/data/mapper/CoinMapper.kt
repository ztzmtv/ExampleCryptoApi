package com.example.mycryptoapp.data.mapper

import com.example.mycryptoapp.data.database.CoinInfoDbModel
import com.example.mycryptoapp.data.network.model.CoinInfoDto
import com.example.mycryptoapp.data.network.model.CoinInfoJsonContainerDto
import com.example.mycryptoapp.data.network.model.CoinNamesListDto
import com.example.mycryptoapp.domain.CoinInfo
import com.google.gson.Gson
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class CoinMapper {
    fun mapDtoToDbModel(dto: CoinInfoDto) = CoinInfoDbModel(
        fromsymbol = dto.fromsymbol,
        tosymbol = dto.tosymbol,
        price = dto.price,
        lastupdate = dto.lastupdate,
        highday = dto.highday,
        lowday = dto.lowday,
        lastmarket = dto.lastmarket,
        imageurl = BASE_IMAGE_URL + dto.imageurl
    )

    fun mapJsonContainerToListCoinInfo(jsonContainer: CoinInfoJsonContainerDto): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val jsonObject = jsonContainer.json ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun mapNamesListToString(namesListDto: CoinNamesListDto): String {
        return namesListDto.names?.map {
            it.coinName?.name
        }?.joinToString(",") ?: ""
    }

    fun mapDbModelToEntity(dbModel: CoinInfoDbModel) = CoinInfo(
        fromsymbol = dbModel.fromsymbol,
        tosymbol = dbModel.tosymbol,
        price = dbModel.price,
        lastupdate = convertTimestampToTime(dbModel.lastupdate),
        highday = dbModel.highday,
        lowday = dbModel.lowday,
        lastmarket = dbModel.lastmarket,
        imageurl = dbModel.imageurl
    )

    private fun convertTimestampToTime(timeStamp: Long?): String {
        if (timeStamp == null) return ""
        val stamp = Timestamp(timeStamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    companion object {
        const val BASE_IMAGE_URL = "https://cryptocompare.com"
    }
}