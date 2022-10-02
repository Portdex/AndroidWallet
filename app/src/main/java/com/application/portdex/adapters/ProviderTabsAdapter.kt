package com.application.portdex.adapters

import android.annotation.SuppressLint
import com.application.portdex.R
import com.application.portdex.databinding.ProviderTabViewBinding
import com.application.portdex.domain.models.category.CategoryData

class ProviderTabsAdapter(val listener: (CategoryData) -> Unit) :
    BaseAdapter<CategoryData>(resource = R.layout.provider_tab_view) {

    private var selectedItemPosition = 0

    @SuppressLint("NotifyDataSetChanged")
    override fun onItemClick(position: Int) {
        if (selectedItemPosition != position) {
            selectedItemPosition = position
            listener.invoke(getItemAtPosition(position))
            notifyDataSetChanged()
        }
    }

    override fun onBind(holder: BaseViewHolder, item: CategoryData) {
        ProviderTabViewBinding.bind(holder.view).apply {
            textView.text = item.name
            textView.isActivated = holder.adapterPosition == selectedItemPosition
        }
    }
}