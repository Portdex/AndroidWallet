package com.application.portdex.adapters

import android.annotation.SuppressLint
import com.application.portdex.R
import com.application.portdex.databinding.ChatTabViewBinding
import com.application.portdex.domain.models.SimpleItem

class ChatTabsAdapter(val listener: (SimpleItem) -> Unit) :
    BaseAdapter<SimpleItem>(resource = R.layout.chat_tab_view) {

    private var selectedItem = 0

    @SuppressLint("NotifyDataSetChanged")
    override fun onItemClick(position: Int) {
        if (selectedItem != position) {
            selectedItem = position
            listener(getItemAtPosition(position))
            notifyDataSetChanged()
        }
    }

    override fun onBind(holder: BaseViewHolder, item: SimpleItem) {
        val view = ChatTabViewBinding.bind(holder.view)
        val isActive = selectedItem == holder.adapterPosition
        view.labelView.apply {
            text = item.label
            isActivated = isActive
        }
        item.icon?.let {
            view.imageView.apply {
                setImageResource(it)
                isActivated = isActive
            }
        }
    }
}