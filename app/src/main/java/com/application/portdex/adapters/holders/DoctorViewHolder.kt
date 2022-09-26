package com.application.portdex.adapters.holders

import android.graphics.drawable.GradientDrawable
import androidx.recyclerview.widget.RecyclerView
import com.application.portdex.core.utils.GenericUtils
import com.application.portdex.databinding.DoctorsListViewBinding
import com.application.portdex.domain.models.DoctorItem

class DoctorViewHolder(val view: DoctorsListViewBinding) : RecyclerView.ViewHolder(view.root) {

    init {
        val colorOne = GenericUtils.getRandomColor()
        val gradient = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(colorOne, GenericUtils.getRandomColor())
        )
        view.mainContainer.background = gradient
        view.btnChat.setTextColor(colorOne)
    }

    fun setData(item: DoctorItem) {
        view.userName.text = item.userName
        view.fieldView.text = item.occupation
    }
}