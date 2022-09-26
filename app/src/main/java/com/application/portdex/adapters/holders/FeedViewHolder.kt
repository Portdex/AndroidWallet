package com.application.portdex.adapters.holders

import androidx.recyclerview.widget.RecyclerView
import com.application.portdex.databinding.FeedItemLayoutBinding
import com.application.portdex.domain.models.FeedItem

class FeedViewHolder(val view: FeedItemLayoutBinding) : RecyclerView.ViewHolder(view.root) {

    fun setData(item: FeedItem) {
        view.feed = item
        view.executePendingBindings()
    }
}