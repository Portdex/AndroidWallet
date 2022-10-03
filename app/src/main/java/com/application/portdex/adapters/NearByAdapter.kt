package com.application.portdex.adapters

import android.graphics.drawable.GradientDrawable
import com.application.portdex.R
import com.application.portdex.core.dataBinding.DataBinding.setUserImage
import com.application.portdex.core.utils.GenericUtils
import com.application.portdex.databinding.DoctorsListViewBinding
import com.application.portdex.domain.models.ProfileInfo

class NearByAdapter : BaseAdapter<ProfileInfo>(resource = R.layout.doctors_list_view) {

    var chatListener: ((ProfileInfo) -> Unit)? = null

    override fun onBind(holder: BaseViewHolder, item: ProfileInfo) {
        val view = DoctorsListViewBinding.bind(holder.view)

        val colorOne = GenericUtils.getRandomColor()
        val gradient = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(colorOne, GenericUtils.getRandomColor())
        )
        view.mainContainer.background = gradient
        view.btnChat.setTextColor(colorOne)

        view.userName.text = item.firstName
        view.fieldView.text = item.category
        view.userImage.setUserImage(item.profilePicUrl)

        view.btnChat.setOnClickListener { chatListener?.invoke(item) }
    }
}