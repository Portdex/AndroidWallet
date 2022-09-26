package com.application.portdex.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.portdex.adapters.holders.DesignerViewHolder
import com.application.portdex.databinding.TopDesignersListViewBinding
import com.application.portdex.domain.models.DesignersItem

class DesignerAdapter : RecyclerView.Adapter<DesignerViewHolder>() {

    private var list = mutableListOf<DesignersItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DesignerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = TopDesignersListViewBinding.inflate(inflater, parent, false)
        return DesignerViewHolder(view)
    }

    override fun onBindViewHolder(holder: DesignerViewHolder, position: Int) {
        holder.setData(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: MutableList<DesignersItem>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size
}