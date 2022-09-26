package com.application.portdex.adapters

import android.graphics.BitmapFactory
import android.view.ViewGroup
import android.widget.ImageView
import androidx.palette.graphics.Palette
import com.application.portdex.R
import com.application.portdex.core.utils.GenericUtils.getImagesBg
import com.application.portdex.domain.models.DigitalDesignItem
import com.google.android.material.textview.MaterialTextView

class DesignsAdapter : BaseAdapter<DigitalDesignItem>(
    R.layout.digital_designs_list_view
) {

    override fun onBind(holder: BaseViewHolder, item: DigitalDesignItem) {
        val container = holder.itemView.findViewById<ViewGroup>(R.id.content_container)
        val labelView = holder.itemView.findViewById<MaterialTextView>(R.id.label_view)
        val imageView = holder.itemView.findViewById<ImageView>(R.id.image_view)

        labelView.text = item.title
        val context = holder.itemView.context
        item.title?.lowercase()?.replace(" ", "_")?.let {
            try {
                context.resources.assets.open("app_designs/${it}.png").let { inputStream ->
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    imageView.setImageBitmap(bitmap)
                    Palette.Builder(bitmap).generate { palette ->
                        palette?.getImagesBg()?.let { drawable ->
                            container.background = drawable
                        }
                    }
                }
            } catch (_: Exception) {
            }
        }
    }

}