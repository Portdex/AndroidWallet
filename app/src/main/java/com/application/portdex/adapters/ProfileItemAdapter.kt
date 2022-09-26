package com.application.portdex.adapters

import com.application.portdex.R
import com.application.portdex.databinding.ProfileItemViewBinding
import com.application.portdex.domain.models.SimpleItem

class ProfileItemAdapter : BaseAdapter<SimpleItem>(resource = R.layout.profile_item_view) {

    override fun onBind(holder: BaseViewHolder, item: SimpleItem) {
        val view = ProfileItemViewBinding.bind(holder.view)
        view.labelView.text = item.label
        item.icon?.let { view.imageView.setImageResource(it) }
    }
}