package com.application.portdex.adapters.chat

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.forEach
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.portdex.adapters.holders.chat.*
import com.application.portdex.core.listeners.OnMessageViewClickListener
import com.application.portdex.core.utils.DateFormatter
import com.application.portdex.databinding.IncomingMessageViewBinding
import com.application.portdex.databinding.ItemDateHeaderBinding
import com.application.portdex.databinding.OutgoingMessageViewBinding
import com.application.portdex.databinding.ProgressListViewBinding
import com.application.portdex.domain.models.chat.ChatItem
import com.application.portdex.domain.models.chat.ChatItem.Companion.getCreatedAt
import com.application.portdex.domain.models.chat.LazyLoading
import com.application.portdex.domain.models.chat.Wrapper
import java.util.*

abstract class BaseChatAdapter constructor(
    private val senderId: String?
) : RecyclerView.Adapter<ViewHolder<*>>() {

    private val VIEW_TYPE_LOADING = 129
    private val VIEW_TYPE_DATE_HEADER = 130
    private val VIEW_TYPE_TEXT_MESSAGE = 131


    protected var items = mutableListOf<Wrapper<Any>>()
    protected var layoutManager: LinearLayoutManager? = null
    var isAtBottom = false


    protected fun getHolder(parent: ViewGroup, viewType: Int): ViewHolder<*> {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_DATE_HEADER -> {
                DateHeaderViewHolder(ItemDateHeaderBinding.inflate(inflater, parent, false))
            }
            VIEW_TYPE_TEXT_MESSAGE -> {
                val view = IncomingMessageViewBinding.inflate(inflater, parent, false)
                IncomingTextMessageViewHolder(view)
            }
            -VIEW_TYPE_TEXT_MESSAGE -> {
                val view = OutgoingMessageViewBinding.inflate(inflater, parent, false)
                OutGoingTextMessageViewHolder(view)
            }
            else -> {
                ChatProgressViewHolder(ProgressListViewBinding.inflate(inflater, parent, false))
            }
        }
    }


    protected fun onBind(
        holder: ViewHolder<*>,
        item: Any,
        isContinuous: Boolean,
        isSameMinute: Boolean,
        dateFormatter: DateFormatter.Formatter,
        clickListenersArray: SparseArray<OnMessageViewClickListener>
    ) {
        if (item is ChatItem) {
            holder.isContinuous = isContinuous
            holder.isSameTime = isSameMinute

            clickListenersArray.forEach { key, _ ->
                holder.itemView.findViewById<View>(key)?.let { view ->
                    view.setOnClickListener {
                        clickListenersArray.get(key).onMessageViewClick(view, item)
                    }
                }
            }
        } else if (item is Date) {
            (holder as DateHeaderViewHolder).dateHeadersFormatter = dateFormatter
        }
        (holder as ViewHolder<Any>).onBind(item)
    }

    fun getLastMessage(): ChatItem? {
        return items.firstOrNull()?.item as? ChatItem
    }

    protected fun isPreviousSameDate(position: Int = 0, dateToCompare: Date): Boolean {
        if (items.size <= position) return false
        return if (items[position].item is ChatItem) {
            val previousPositionDate = (items[position].item as ChatItem).getCreatedAt()
            DateFormatter.isSameDay(dateToCompare, previousPositionDate)
        } else false
    }

    protected fun isPreviousSameAuthor(position: Int = 0, id: String?): Boolean {
        val prevPosition = position + 1
        return if (items.size <= prevPosition) false
        else items[prevPosition].item is ChatItem && (items[prevPosition].item as ChatItem).sender == id
    }

    protected fun isContinuous(
        currentMsg: Wrapper<Any>?,
        precedingMsg: Wrapper<Any>?
    ): Boolean {
        if (currentMsg == null || precedingMsg == null) return false
        val currentUser: String?
        val precedingUser: String?
        if (currentMsg.item is ChatItem) {
            currentUser = ((currentMsg.item) as ChatItem).sender
        } else return false

        if (precedingMsg.item is ChatItem) {
            precedingUser = ((precedingMsg.item) as ChatItem).sender
        } else return false

        return !(currentUser.isNullOrEmpty() || precedingUser.isNullOrEmpty()) && currentUser == precedingUser
    }

    protected fun isSameMinute(
        currentMsg: Wrapper<Any>?,
        precedingMsg: Wrapper<Any>?
    ): Boolean {
        if (currentMsg == null || precedingMsg == null) return false
        val currentTime: Date
        val precedingTime: Date
        if (currentMsg.item is ChatItem) {
            currentTime = ((currentMsg.item) as ChatItem).getCreatedAt()
        } else return false

        if (precedingMsg.item is ChatItem) {
            precedingTime = ((precedingMsg.item) as ChatItem).getCreatedAt()
        } else return false

        return DateFormatter.isSameMinute(currentTime, precedingTime)
    }

    protected fun getMessagePositionById(id: Int): Int {
        var itemIndex = -1
        items.forEachIndexed { index, wrapper ->
            if (wrapper.item is ChatItem) {
                if ((wrapper.item as ChatItem).id == id) {
                    itemIndex = index
                }
            }
        }
        return itemIndex
    }


    override fun getItemViewType(position: Int): Int {
        var isOutGoing = false
        val viewType: Int
        when (val item = items[position].item) {
            is LazyLoading -> viewType = VIEW_TYPE_LOADING
            is ChatItem -> {
                isOutGoing = item.sender == senderId
                viewType = getContentViewType(item)
            }
            else -> viewType = VIEW_TYPE_DATE_HEADER
        }
        return if (isOutGoing) viewType * -1 else viewType
    }

    private fun getContentViewType(message: ChatItem): Int {
        return VIEW_TYPE_TEXT_MESSAGE
    }

    fun scrollToBottom() {
        layoutManager?.scrollToPosition(0)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        layoutManager = recyclerView.layoutManager as? LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val firstVisibleItem = layoutManager?.findFirstVisibleItemPosition()
                isAtBottom = firstVisibleItem == 0
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    override fun getItemCount(): Int = items.size
}