package com.application.portdex.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.portdex.adapters.holders.DoctorViewHolder
import com.application.portdex.databinding.DoctorsListViewBinding
import com.application.portdex.domain.models.DoctorItem

class DoctorAdapter : RecyclerView.Adapter<DoctorViewHolder>() {

    private var list = mutableListOf<DoctorItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DoctorsListViewBinding.inflate(inflater, parent, false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        holder.setData(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: MutableList<DoctorItem>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size
}