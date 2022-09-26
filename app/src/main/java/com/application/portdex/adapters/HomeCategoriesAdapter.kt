package com.application.portdex.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.application.portdex.adapters.holders.CategoryViewHolder
import com.application.portdex.domain.models.category.CategoryData

class HomeCategoriesAdapter(
    @LayoutRes val resource: Int,
    private val listener: ((CategoryData) -> Unit)? = null
) : RecyclerView.Adapter<CategoryViewHolder>() {

    private var list = mutableListOf<CategoryData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(resource, parent, false)
        return CategoryViewHolder(view, listener())
    }

    private fun listener(): (Int) -> Unit {
        return { position ->
            listener?.invoke(getItemAt(position))
        }
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bindData(getItemAt(position))
    }

    private fun getItemAt(position: Int): CategoryData {
        return list[position]
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: List<CategoryData>) {
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    override fun getItemId(position: Int): Long {
        return list[position].id.hashCode().toLong()
    }
}