package com.application.portdex.adapters

import android.widget.ImageView
import com.application.portdex.R
import com.application.portdex.core.dataBinding.DataBinding.setImage
import com.application.portdex.domain.models.ProviderPackage
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class ProvidePackagesAdapter : BaseAdapter<ProviderPackage>(resource = R.layout.package_item_view) {

    var cartItemListener: ((ProviderPackage) -> Unit)? = null

    override fun onBind(holder: BaseViewHolder, item: ProviderPackage) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.image_view)
        val nameView = holder.itemView.findViewById<MaterialTextView>(R.id.provider_name)
        val durationView = holder.itemView.findViewById<MaterialTextView>(R.id.sub_category)
        val priceView = holder.itemView.findViewById<MaterialTextView>(R.id.price_view)
        val btnCart = holder.itemView.findViewById<MaterialButton>(R.id.btn_cart)

        imageView.setImage(item.icon)
        nameView.text = item.name
        durationView.text = item.duration
        priceView.text = item.price
        btnCart.setText(if (item.isCartItem) R.string.button_remove_from_card else R.string.button_add_to_cart)

        btnCart.setOnClickListener { cartItemListener?.invoke(item) }
    }

    fun updateItem(item: ProviderPackage) {
        list.find { it.packageId == item.packageId }?.let {
            val index = list.indexOf(it)
            if (index != -1) {
                list[index] = item
                notifyItemChanged(index)
            }
        }
    }

    fun removedCartItems(packageIds: List<String>) {
        list.forEachIndexed { index, item ->
            if (packageIds.any { it == item.packageId }) {
                item.isCartItem = false
                list[index] = item
                notifyItemChanged(index)
            }
        }
    }
}