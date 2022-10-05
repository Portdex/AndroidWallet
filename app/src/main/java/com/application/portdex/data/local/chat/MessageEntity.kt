package com.application.portdex.data.local.chat

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.application.portdex.core.enums.MessageType
import com.application.portdex.domain.models.chat.MessageStatus


@Entity(tableName = "messages", indices = [Index(value = ["messageId"], unique = true)])
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val messageId: String?,
    val userName: String?,
    val userImage: String?,
    val sender: String?,
    val receiver: String?,
    val chatUserId: String?,
    var body: String? = null,
    var type: MessageType = MessageType.Text,
    var status: MessageStatus = MessageStatus.sending,
    val createdAt: Long = System.currentTimeMillis()
)
