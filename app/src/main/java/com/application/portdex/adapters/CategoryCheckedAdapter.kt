package com.application.portdex.adapters

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.application.portdex.R
import com.application.portdex.domain.models.category.CategoryData
import com.google.android.material.textview.MaterialTextView

class CategoryCheckedAdapter(
    resource: Int,
    private val listener: ((CategoryData) -> Unit)? = null
) : BaseAdapter<CategoryData>(resource) {

    private var selectedItem = RecyclerView.NO_POSITION

    @SuppressLint("NotifyDataSetChanged")
    override fun onItemClick(position: Int) {
        getItemAtPosition(position).let { data ->
            selectedItem = position
            notifyDataSetChanged()
        }
    }

    override fun onBind(holder: BaseViewHolder, item: CategoryData) {
        val context = holder.itemView.context
        val mainView = holder.itemView.findViewById<ViewGroup>(R.id.main_container)
        val title = holder.itemView.findViewById<MaterialTextView>(R.id.label_view)
        val imageView = holder.itemView.findViewById<ImageView>(R.id.image_view)

        mainView?.isActivated = selectedItem == holder.adapterPosition
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