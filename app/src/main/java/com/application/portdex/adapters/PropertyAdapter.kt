package com.application.portdex.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.portdex.adapters.holders.PropertyViewHolder
import com.application.portdex.databinding.PropertiesListViewBinding
import com.application.portdex.domain.models.PropertyItem

class PropertyAdapter : RecyclerView.Adapter<PropertyViewHolder>() {

    private var list = mutableListOf<PropertyItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = PropertiesListViewBinding.inflate(inflater, parent, false)
        return PropertyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        holder.setData(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: MutableList<PropertyItem>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size
}