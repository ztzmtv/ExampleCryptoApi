package com.example.mycryptoapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.mycryptoapp.domain.CoinInfo

object CoinInfoDiffCallback : DiffUtil.ItemCallback<CoinInfo>() {
    override fun areItemsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return oldItem.fromsymbol == newItem.fromsymbol
    }

    override fun areContentsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return oldItem == newItem
    }
}