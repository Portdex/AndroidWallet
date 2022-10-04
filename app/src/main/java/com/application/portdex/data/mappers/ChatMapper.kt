package com.application.portdex.data.mappers

import com.application.portdex.data.local.chat.MessageEntity
import com.application.portdex.domain.models.chat.ChatItem


fun List<MessageEntity>.toChatList(): MutableList<ChatItem> {
    return map {
        ChatItem(
            id = it.id,
            userName = it.userName,
            userImage = it.userImage,
            sender = it.sender,
            receiver = it.receiver,
            body = it.body,
            type = it.type,
            status = it.status,
            createdAt = it.createdAt
        )
    }.toMutableList()
}