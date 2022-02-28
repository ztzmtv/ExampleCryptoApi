package com.example.mycryptoapp.data.paging

import android.app.Application
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mycryptoapp.data.network.ApiService
import com.example.mycryptoapp.domain.CoinInfo

class CoinPagingSource(
    private val application: Application,
    private val service: ApiService
) : PagingSource<Int, CoinInfo>() {
    override fun getRefreshKey(state: PagingState<Int, CoinInfo>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CoinInfo> {
        val pageNumber = params.key ?: STARTING_PAGE_INDEX
        try {
            val response = service.getTopCoinsInfo(
                page = pageNumber
            )
        } catch (e: Exception) {
        }
        TODO("Not yet implemented")
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 0

    }
}