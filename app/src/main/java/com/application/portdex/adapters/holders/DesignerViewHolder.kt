package com.application.portdex.adapters.holders

import androidx.recyclerview.widget.RecyclerView
import com.application.portdex.databinding.TopDesignersListViewBinding
import com.application.portdex.domain.models.DesignersItem

class DesignerViewHolder(val view: TopDesignersListViewBinding) :
    RecyclerView.ViewHolder(view.root) {

    fun setData(item: DesignersItem) {
        view.userName.text = item.userName
        view.ratingView.text = item.rating
        view.activeStatue.isActivated = item.isActive
    }
}