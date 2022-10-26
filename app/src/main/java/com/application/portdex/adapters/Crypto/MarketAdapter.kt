package com.application.portdex.adapters.Crypto

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.portdex.Models.DataM
import com.application.portdex.R
import com.application.portdex.databinding.ItemCryptoMarketBinding

class MarketAdapter (var context : Context,var list : MutableList<DataM>): RecyclerView.Adapter<MarketAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_crypto_market, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list.get(position)
        with(holder){
            binding.tvName.setText(data.name)
            binding.tvShortName.setText(data.symbol)
            binding.tvRate.setText("${data.quote.USD.volume_change_24h}")
            binding.tvPercentChange.setText("${String.format("%.2f", data.quote.USD.percent_change_24h)}%")



            if(data.quote.USD.percent_change_24h.toString().contains("-")){
                binding.ivDown.visibility = View.VISIBLE
                binding.ivUp.visibility = View.GONE
                binding.tvPercentChange.setTextColor(Color.parseColor("#FF0000"))
            }else{
                binding.tvPercentChange.setTextColor(Color.parseColor("#7ED321"))
                binding.ivDown.visibility = View.GONE
                binding.ivUp.visibility = View.VISIBLE
            }
        }

    }

    override fun getItemCount(): Int {
      return  list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemCryptoMarketBinding.bind(itemView)

    }

}