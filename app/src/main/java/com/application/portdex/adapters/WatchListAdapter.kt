package com.kds.cryptodesign

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.portdex.R

class WatchListAdapter(var context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {


    companion object {
        private const val type = 3
        private const val Lost = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == type){
            return ProfitViewHolder(
                LayoutInflater.from(context)
                    .inflate(R.layout.item_watchlist_view, parent, false)
            )
        }
        return LostViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_watchlist_loss_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return 5
    }

    inner class ProfitViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

    }
    inner class LostViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {


    }

    override fun getItemViewType(position: Int): Int {
//        return list[position]?.type!!.toInt()
        if (position == 1 || position == 3 )
            return 3
        else
            return 2

    }
}