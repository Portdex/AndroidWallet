package com.application.portdex.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.application.portdex.core.listeners.ItemClickListener

abstract class BaseAdapter<T>(@LayoutRes val resource: Int) :
    RecyclerView.Adapter<BaseAdapter.BaseViewHolder>(), ItemClickListener {

    abstract fun onBind(holder: BaseViewHolder, item: T)

    private var list = mutableListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(resource, parent, false)
        return BaseViewHolder(view, this)
    }

    override fun onItemClick(position: Int) {

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        onBind(holder, getItemAtPosition(position))
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: MutableList<T>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun getItemAtPosition(position: Int): T {
        return list[position]
    }

    override fun getItemCount() = list.size

    class BaseViewHolder(val view: View, listener: ItemClickListener) :
        RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {
                if (adapterPosition != -1) listener.onItemClick(adapterPosition)
            }
        }

    }

}