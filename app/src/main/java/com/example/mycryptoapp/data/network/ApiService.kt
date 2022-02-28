package com.example.mycryptoapp.data.network

import com.example.mycryptoapp.data.network.model.CoinInfoJsonContainerDto
import com.example.mycryptoapp.data.network.model.CoinNamesListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //https://medium.com/android-beginners/mvvm-with-kotlin-coroutines-and-retrofit-example-d3f5f3b09050
    //НЕ НАЧИНАТЬ С СИМВОЛА "/"

    @GET("top/totalvolfull")
    suspend fun getTopCoinsInfo(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = DEFAULT_PARAM_EMPTY_STRING,
        @Query(QUERY_PARAM_LIMIT) limit: Int = DEFAULT_PARAM_LIMIT,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = DEFAULT_PARAM_CURRENCY, //required
        @Query(QUERY_PARAM_PAGE) page: Int = DEFAULT_PARAM_PAGE
    ): CoinNamesListDto

    @GET("pricemultifull")
    suspend fun getFullPriceList(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = DEFAULT_PARAM_EMPTY_STRING,
        @Query(QUERY_PARAM_FROM_SYMBOLS) fSyms: String,
        @Query(QUERY_PARAM_TO_SYMBOLS) tSyms: String = DEFAULT_PARAM_CURRENCY
    ): CoinInfoJsonContainerDto

    companion object {
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_PAGE = "page"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
        private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"

        private const val DEFAULT_PARAM_EMPTY_STRING = ""
        private const val DEFAULT_PARAM_CURRENCY = "USD"
        private const val DEFAULT_PARAM_PAGE = 0
        private const val DEFAULT_PARAM_LIMIT = 100
    }
}