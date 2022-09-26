package com.application.portdex.adapters

import android.graphics.BitmapFactory
import android.widget.ImageView
import com.application.portdex.R
import com.application.portdex.domain.models.DigitalDesignItem
import com.google.android.material.textview.MaterialTextView

class DigitalAdapter(resource: Int) : BaseAdapter<DigitalDesignItem>(resource) {

    override fun onBind(holder: BaseViewHolder, item: DigitalDesignItem) {
        val labelView = holder.itemView.findViewById<MaterialTextView>(R.id.label_view)
        val imageView = holder.itemView.findViewById<ImageView>(R.id.image_view)

        labelView?.text = item.title
        val context = holder.itemView.context
        item.title?.lowercase()?.replace(" ", "_")?.let {
            try {
                context.resources.assets.open("${item.thumbnail}/${it}.png").let { inputStream ->
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    imageView?.setImageBitmap(bitmap)
                }
            } catch (_: Exception) {
            }
        }
    }

}