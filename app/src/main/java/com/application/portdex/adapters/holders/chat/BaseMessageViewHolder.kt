package com.application.portdex.adapters.holders.chat

import android.content.Context
import android.view.View
import com.application.portdex.R
import com.application.portdex.core.utils.DateFormatter
import com.application.portdex.core.utils.FormatUtils.formatTo
import com.application.portdex.core.utils.GenericUtils.hide
import com.application.portdex.domain.models.chat.ChatItem
import com.google.android.material.textview.MaterialTextView

open class BaseMessageViewHolder(view: View) : ViewHolder<ChatItem>(view) {

    protected val context: Context = view.context
    private val messageTime: MaterialTextView? = view.findViewById(R.id.messageTime)

    override fun onBind(data: ChatItem) {
        messageTime?.text = data.createdAt.formatTo().let { date ->
            DateFormatter.format(date, DateFormatter.Template.TIME)
        }
        messageTime?.hide(isSameTime)
    }

    protected fun getString(resource: Int): String {
        return context.getString(resource)
    }
}