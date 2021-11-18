package com.example.mycryptoapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mycryptoapp.R
import com.example.mycryptoapp.pojo.CoinPriceInfo
import com.squareup.picasso.Picasso


class CoinInfoAdapter : RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinInfoList: List<CoinPriceInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class CoinInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivLogoCoin: ImageView = itemView.findViewById<ImageView>(R.id.ivLogoCoin)
        val tvSymbols: TextView = itemView.findViewById<TextView>(R.id.tvSymbols)
        val tvPrice: TextView = itemView.findViewById<TextView>(R.id.tvPrice)
        val tvLastUpdate: TextView = itemView.findViewById<TextView>(R.id.tvLastUpdate)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_coin_info, parent, false)
        return CoinInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        with(holder) {
            with(coin) {
                tvSymbols.text = fromsymbol + " / " + coin.tosymbol
                tvPrice.text = price.toString()
                tvLastUpdate.text = getFormattedTime()
                Picasso.get().load(coin.getFullImageUrl()).into(holder.ivLogoCoin)
            }
        }

    }

    override fun getItemCount(): Int = coinInfoList.size
}