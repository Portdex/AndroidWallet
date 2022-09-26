package com.application.portdex.adapters

import com.application.portdex.R
import com.application.portdex.core.dataBinding.DataBinding.setImage
import com.application.portdex.databinding.FeedImageListViewBinding
import com.application.portdex.domain.models.category.SubCategory

class FeedImagesAdapter : BaseAdapter<SubCategory>(resource = R.layout.feed_image_list_view) {

    override fun onBind(holder: BaseViewHolder, item: SubCategory) {
        val view = FeedImageListViewBinding.bind(holder.view)
        item.productImage?.let { view.imageView.setImage(it) }
    }
}