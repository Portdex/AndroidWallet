package com.application.portdex.adapters.chat

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.portdex.adapters.holders.chat.ThreadsViewHolder
import com.application.portdex.databinding.ThreadsListViewBinding
import com.application.portdex.domain.models.chat.Threads
import java.util.*

class ThreadsAdapter(val listener: (Threads) -> Unit) : RecyclerView.Adapter<ThreadsViewHolder>() {

    private var list: MutableList<Threads> = LinkedList()

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThreadsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ThreadsListViewBinding.inflate(inflater, parent, false)
        return ThreadsViewHolder(view, listener())
    }

    private fun listener(): (Int) -> Unit {
        return { position -> listener(list[position]) }
    }

    override fun onBindViewHolder(holder: ThreadsViewHolder, position: Int) {
        holder.setData(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: MutableList<Threads>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    override fun getItemId(position: Int): Long {
        return list[position].id.hashCode().toLong()
    }
}