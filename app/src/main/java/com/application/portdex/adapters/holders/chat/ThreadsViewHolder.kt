package com.application.portdex.adapters.holders.chat

import androidx.recyclerview.widget.RecyclerView
import com.application.portdex.core.dataBinding.DataBinding.setUserImage
import com.application.portdex.core.utils.DateFormatter
import com.application.portdex.core.utils.FormatUtils.formatTo
import com.application.portdex.core.utils.GenericUtils.show
import com.application.portdex.databinding.ThreadsListViewBinding
import com.application.portdex.domain.models.chat.Threads

class ThreadsViewHolder(val view: ThreadsListViewBinding, listener: (Int) -> Unit) :
    RecyclerView.ViewHolder(view.root) {

    init {
        view.root.setOnClickListener {
            if (adapterPosition != -1) listener.invoke(adapterPosition)
        }
    }

    fun setData(thread: Threads) {
        view.userName.text = thread.userName
        view.userImage.setUserImage(thread.userImage)
        view.messageView.text = thread.body
        view.unreadCounts.text = thread.unreadCounts.toString()
        view.dateView.text = thread.createdAt.formatTo().let { date ->
            DateFormatter.format(date, DateFormatter.Template.TIME)
        }
        view.unreadCounts.show(thread.unreadCounts > 0)
    }
}