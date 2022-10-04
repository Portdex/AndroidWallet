package com.application.portdex.adapters.holders.chat

import com.application.portdex.databinding.IncomingMessageViewBinding
import com.application.portdex.domain.models.chat.ChatItem

open class IncomingTextMessageViewHolder(view: IncomingMessageViewBinding) :
    BaseIncomingMessageViewHolder(view.root) {
    private val messageText = view.messageText

    override fun onBind(data: ChatItem) {
        super.onBind(data)
        messageText.text = data.body
    }
}