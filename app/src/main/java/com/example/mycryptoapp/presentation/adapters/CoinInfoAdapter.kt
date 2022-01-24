package com.example.mycryptoapp.presentation.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.mycryptoapp.R
import com.example.mycryptoapp.databinding.ItemCoinInfoBinding
import com.example.mycryptoapp.domain.CoinInfo
import com.squareup.picasso.Picasso


class CoinInfoAdapter(
    private val context: Context
) : ListAdapter<CoinInfo, CoinInfoViewHolder>(CoinInfoDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding =
            ItemCoinInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return CoinInfoViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: CoinInfoViewHolder, position: Int) {
        val coin = getItem(position)
        with(viewHolder.binding) {
            with(coin) {
                val symbolsTemplate = context.resources.getString(R.string.symbols_template)
                val last_update_template =
                    context.resources.getString(R.string.last_update_template)
                tvSymbols.text = String.format(symbolsTemplate, fromsymbol, tosymbol)
                log("$fromsymbol $tosymbol")
                tvPrice.text = price.toString()
                log("$price")
                tvLastUpdateTime.text =
                    String.format(last_update_template, lastupdate)
                log("$lastupdate")
                Picasso.get().load(imageurl).into(viewHolder.binding.ivLogoCoin)
                root.setOnClickListener {
                    onCoinClickListener?.onCoinClick(this)
                }
            }
        }
    }


    interface OnCoinClickListener {
        fun onCoinClick(coinPriceInfo: CoinInfo)
    }

    var onCoinClickListener: OnCoinClickListener? = null

    companion object {
        private const val TAG = "CoinInfoAdapter_TAG"

        private fun log(string: String) {
            Log.d(TAG, string)
        }
    }
}