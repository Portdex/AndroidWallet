package com.application.portdex.domain.models.chat

import com.application.portdex.core.enums.MessageType


data class Threads(
    val id: Int = 0,
    val userName: String?,
    val userImage: String?,
    val chatUserId: String?,
    var body: String? = null,
    var unreadCounts: Int = 0,
    var type: MessageType = MessageType.Text,
    var status: MessageStatus = MessageStatus.sending,
    val createdAt: Long = System.currentTimeMillis(),
    var updatedAt: Long = System.currentTimeMillis()
)
