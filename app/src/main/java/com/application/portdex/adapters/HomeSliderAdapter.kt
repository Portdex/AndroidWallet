package com.application.portdex.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.portdex.databinding.HomeSliderViewBinding
import com.application.portdex.domain.models.HomeSliderItem

class HomeSliderAdapter : RecyclerView.Adapter<HomeSliderAdapter.SliderHolder>() {

    private var list = mutableListOf<HomeSliderItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = HomeSliderViewBinding.inflate(inflater, parent, false)
        return SliderHolder(view)
    }

    override fun onBindViewHolder(holder: SliderHolder, position: Int) {
        holder.setData(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: MutableList<HomeSliderItem>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    class SliderHolder(val view: HomeSliderViewBinding) : RecyclerView.ViewHolder(view.root) {

        fun setData(item: HomeSliderItem) {
            item.image?.let { view.imageView.setImageResource(it) }
        }
    }
}