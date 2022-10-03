package com.application.portdex.adapters

import android.annotation.SuppressLint
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import com.application.portdex.R
import com.application.portdex.core.dataBinding.DataBinding.setImage
import com.application.portdex.domain.models.ProviderInfo
import com.google.android.material.button.MaterialButton

class ServiceProviderAdapter(val listener: (ProviderInfo) -> Unit) : BaseAdapter<ProviderInfo>(
    resource = R.layout.service_provider_item_view
), Filterable {

    var emptyResultListener: ((Boolean) -> Unit)? = null
    var chatListener: ((ProviderInfo) -> Unit)? = null

    override fun onItemClick(position: Int) {
        listener(getItemAtPosition(position))
    }

    override fun onBind(holder: BaseViewHolder, item: ProviderInfo) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.image_view)
        val nameView = holder.itemView.findViewById<TextView>(R.id.provider_name)
        val subCategory = holder.itemView.findViewById<TextView>(R.id.sub_category)
        val ratingView = holder.itemView.findViewById<TextView>(R.id.rating_view)
        val actionChat = holder.itemView.findViewById<MaterialButton>(R.id.btn_chat)

        imageView.setImage(item.profilePicUrl)
        nameView.text = item.firstName
        subCategory.text = item.subCategory
        ratingView.text = item.rating

        actionChat.setOnClickListener { chatListener?.invoke(item) }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint?.toString()
                filteredList = if (charSearch.isNullOrEmpty()) list
                else {
                    list.filter {
                        it.subCategory?.equals(charSearch, ignoreCase = true) == true
                    }.toMutableList()
                }
                return FilterResults().apply { values = filteredList }
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(p0: CharSequence?, results: FilterResults) {
                filteredList = results.values as MutableList<ProviderInfo>
                emptyResultListener?.invoke(filteredList.isEmpty())
                notifyDataSetChanged()
            }
        }
    }
}