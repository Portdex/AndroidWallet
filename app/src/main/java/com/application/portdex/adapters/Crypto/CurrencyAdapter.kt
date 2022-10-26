package com.application.portdex.adapters.Crypto
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.portdex.Models.CurrData
import com.application.portdex.R
import com.application.portdex.databinding.ItemCryptoCurrencyBinding

class CurrencyAdapter(var context : Context, var list : MutableList<CurrData>): RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_crypto_currency, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list.get(position)
        with(holder){
            binding.tvName.setText(data.name)
                binding.tvShortName.setText(data.min_size)


        }

    }

    override fun getItemCount(): Int {
        return  list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemCryptoCurrencyBinding.bind(itemView)

    }

}