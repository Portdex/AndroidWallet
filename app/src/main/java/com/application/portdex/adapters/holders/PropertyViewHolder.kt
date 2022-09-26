package com.application.portdex.adapters.holders

import androidx.recyclerview.widget.RecyclerView
import com.application.portdex.databinding.PropertiesListViewBinding
import com.application.portdex.domain.models.PropertyItem

class PropertyViewHolder(val view: PropertiesListViewBinding) :
    RecyclerView.ViewHolder(view.root) {

    fun setData(item: PropertyItem) {
        view.userName.text = item.userName
        view.designationView.text = item.designation
        view.activeStatue.isActivated = item.isActive
    }
}