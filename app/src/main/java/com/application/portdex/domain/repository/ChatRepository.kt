package com.application.portdex.domain.repository

import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.models.chat.ChatBody
import com.application.portdex.domain.models.chat.ChatItem

interface ChatRepository {

    fun initChatManager(userId: String)
    fun getChatList(
        sender: String?,
        receiver: String?,
        listener: (Resource<MutableList<ChatItem>>) -> Unit
    )

    fun sendMessage(chatBody: ChatBody.Builder)
    fun onClear()
}