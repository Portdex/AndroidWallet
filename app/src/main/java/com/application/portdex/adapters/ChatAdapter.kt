package com.application.portdex.adapters

import android.content.Context
import android.util.SparseArray
import android.view.ViewGroup
import com.application.portdex.R
import com.application.portdex.adapters.chat.BaseChatAdapter
import com.application.portdex.adapters.holders.chat.ViewHolder
import com.application.portdex.core.listeners.OnMessageViewClickListener
import com.application.portdex.core.utils.DateFormatter
import com.application.portdex.domain.models.chat.ChatItem
import com.application.portdex.domain.models.chat.ChatItem.Companion.getCreatedAt
import com.application.portdex.domain.models.chat.Wrapper
import java.util.*


class ChatAdapter(
    senderId: String?,
    val context: Context
) : BaseChatAdapter(senderId), DateFormatter.Formatter {

    private val viewClickListenersArray = SparseArray<OnMessageViewClickListener>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<*> {
        return getHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder<*>, position: Int) {
        val wrapper = items[position]
        var isContinuous = false
        var isSameMinute = false
        if (position > 0 && position != items.size - 1) {
            isContinuous = isContinuous(items[position], items[position - 1])
            isSameMinute = isSameMinute(items[position], items[position - 1])
        }
        onBind(holder, wrapper.item, isContinuous, isSameMinute, this, viewClickListenersArray)
    }

    fun addToStart(list: MutableList<ChatItem>, scroll: Boolean = false) {
        list.forEach { message -> if (!update(message)) addToStart(message, scroll) }
    }

    private fun addToStart(message: ChatItem, scroll: Boolean = false) {
        val isNewMessageToday = !isPreviousSameDate(dateToCompare = message.getCreatedAt())
        if (isNewMessageToday) items.add(0, Wrapper(message.getCreatedAt()))
        items.add(0, Wrapper(message))
        val isContinuous = isPreviousSameAuthor(id = message.sender)
        notifyItemRangeInserted(0, if (isNewMessageToday) 2 else 1)
        if (isContinuous) notifyItemChanged(1)
        if (scroll && isAtBottom) layoutManager?.scrollToPosition(0)
    }

    fun addToEnd(messages: MutableList<ChatItem>) {
        if (messages.isEmpty()) return
        if (items.isNotEmpty()) {
            val lastItemPosition = items.size - 1
            val lastItem = items[lastItemPosition].item as Date
            if (DateFormatter.isSameDay(messages[0].getCreatedAt(), lastItem)) {
                items.removeAt(lastItemPosition)
                notifyItemRemoved(lastItemPosition)
            }
        }
        val oldSize = items.size
        generateDateHeaders(messages)
        notifyItemRangeInserted(oldSize, items.size - oldSize)
    }

    fun update(message: ChatItem): Boolean {
        var update = false
        items.filter { it.item is ChatItem }.find { (it.item as ChatItem).id == message.id }
            ?.let { wrapper ->
                val index = items.indexOf(wrapper)
                if (index != -1) {
                    items[index] = Wrapper(message)
                    notifyItemChanged(index)
                    update = true
                }
            }
        return update
    }

    private fun generateDateHeaders(messages: List<ChatItem>) {
        for (i in messages.indices) {
            val message = messages[i]
            items.add(Wrapper(message))
            if (messages.size > i + 1) {
                val nextMessage = messages[i + 1]
                if (!DateFormatter.isSameDay(message.getCreatedAt(), nextMessage.getCreatedAt())) {
                    items.add(Wrapper(message.getCreatedAt()))
                }
            } else {
                items.add(Wrapper(message.getCreatedAt()))
            }
        }
    }

    fun registerViewClickListener(
        viewId: Int,
        onMessageViewClickListener: OnMessageViewClickListener
    ) {
        this.viewClickListenersArray.append(viewId, onMessageViewClickListener)
    }

    override fun format(date: Date): String {
        return when {
            DateFormatter.isToday(date) -> context.getString(R.string.label_date_today)
            DateFormatter.isYesterday(date) -> context.getString(R.string.label_date_yesterday)
            else -> DateFormatter.format(date, DateFormatter.Template.STRING_DAY_MONTH_YEAR)
        }
    }

}