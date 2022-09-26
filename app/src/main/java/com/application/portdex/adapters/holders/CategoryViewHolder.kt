package com.application.portdex.adapters.holders

import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.application.portdex.R
import com.application.portdex.domain.models.category.CategoryData
import com.google.android.material.textview.MaterialTextView

class CategoryViewHolder(val view: View, listener: (Int) -> Unit) : RecyclerView.ViewHolder(view) {

    private val context = view.context
    private val title = view.findViewById<MaterialTextView>(R.id.label_view)
    private val imageView = view.findViewById<ImageView>(R.id.image_view)

    init {
        view.setOnClickListener {
            if (adapterPosition != -1) listener.invoke(adapterPosition)
        }
    }

    fun bindData(item: CategoryData) {
        title.text = item.name
        item.image?.lowercase()?.replace(" ", "_")?.let {
            try {
                context.resources.assets.open(it).let { inputStream ->
                    imageView.setImageBitmap(BitmapFactory.decodeStream(inputStream))
                }
            } catch (_: Exception) {

            }
        }
    }
}