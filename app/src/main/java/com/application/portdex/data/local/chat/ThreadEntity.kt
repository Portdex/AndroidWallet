package com.application.portdex.data.local.chat

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.application.portdex.core.enums.MessageType
import com.application.portdex.domain.models.chat.MessageStatus

@Entity(tableName = "threads", indices = [Index(value = ["threadId"], unique = true)])
data class ThreadEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userName: String?,
    val userImage: String?,
    val threadId: String?,
    var body: String? = null,
    var unreadCounts: Int = 0,
    var type: MessageType = MessageType.Text,
    var status: MessageStatus = MessageStatus.sending,
    val createdAt: Long = System.currentTimeMillis(),
    var updatedAt: Long = System.currentTimeMillis()
)