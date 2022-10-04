package com.application.portdex.domain.viewmodels

import androidx.lifecycle.ViewModel
import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.models.chat.ChatBody
import com.application.portdex.domain.models.chat.ChatItem
import com.application.portdex.domain.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ChatRepository
) : ViewModel() {

    fun initChatManager(userId: String) {
        repository.initChatManager(userId)
    }

    fun getChatList(
        sender: String?,
        receiver: String?,
        listener: (Resource<MutableList<ChatItem>>) -> Unit
    ) {
        repository.getChatList(sender, receiver, listener)
    }

    fun sendMessage(chatBody: ChatBody.Builder) {
        repository.sendMessage(chatBody)
    }
}