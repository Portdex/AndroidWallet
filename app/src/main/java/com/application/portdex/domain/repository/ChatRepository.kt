package com.application.portdex.domain.repository

import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.models.ProfileInfo
import com.application.portdex.domain.models.chat.ChatBody
import com.application.portdex.domain.models.chat.ChatItem
import com.application.portdex.domain.models.chat.Threads

interface ChatRepository {

    fun initChatUser(userId: String)
    fun getThreadsList(listener: (Resource<List<Threads>>) -> Unit)
    fun getChatList(listener: (Resource<MutableList<ChatItem>>) -> Unit)
    fun resetUnreadCounts()
    fun sendMessage(chatBody: ChatBody.Builder)
    fun newMessageListener(listener: (ProfileInfo, String) -> Unit)
    fun cleanChat()
    fun onClear()
}