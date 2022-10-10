package com.application.portdex.adapters

import android.widget.ImageView
import com.application.portdex.R
import com.application.portdex.core.dataBinding.DataBinding.setImage
import com.application.portdex.domain.models.ProviderPackage
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class CartAdapter(val listener: (ProviderPackage) -> Unit) :
    BaseAdapter<ProviderPackage>(resource = R.layout.cart_item_view) {

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

        btnCart.setOnClickListener { listener.invoke(item) }
    }
}