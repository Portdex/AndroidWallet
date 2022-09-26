package com.application.portdex.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.portdex.adapters.holders.FeedViewHolder
import com.application.portdex.databinding.FeedItemLayoutBinding
import com.application.portdex.domain.models.FeedItem
import java.util.*

class FeedsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: MutableList<FeedItem> = LinkedList()

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = FeedItemLayoutBinding.inflate(inflater, parent, false)
        return FeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FeedViewHolder) holder.setData(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: MutableList<FeedItem>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    override fun getItemId(position: Int): Long {
        return list[position].postId.hashCode().toLong()
    }
}