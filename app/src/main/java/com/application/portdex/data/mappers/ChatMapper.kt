package com.application.portdex.data.mappers

import com.application.portdex.data.local.chat.MessageEntity
import com.application.portdex.data.local.chat.ThreadEntity
import com.application.portdex.domain.models.chat.ChatItem
import com.application.portdex.domain.models.chat.Threads


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

fun List<ThreadEntity>.toThreadsList(): MutableList<Threads> {
    return map {
        Threads(
            id = it.id,
            userName = it.userName,
            userImage = it.userImage,
            chatUserId = it.threadId,
            body = it.body,
            type = it.type,
            unreadCounts = it.unreadCounts,
            status = it.status,
            createdAt = it.createdAt,
            updatedAt = it.updatedAt
        )
    }.toMutableList()
}