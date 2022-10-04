package com.application.portdex.adapters.holders.chat

import android.view.View
import androidx.recyclerview.widget.RecyclerView


abstract class ViewHolder<DATA : Any>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var isContinuous = false
    var isSameTime = false
    abstract fun onBind(data: DATA)
}