package com.application.portdex.domain.models.chat

import androidx.annotation.Keep
import com.application.portdex.core.enums.MessageType
import com.application.portdex.core.utils.FormatUtils.formatTo
import java.util.*

@Keep
data class ChatItem(
    var id: Int = 0,
    val userName: String?,
    val userImage: String?,
    val sender: String?,
    val receiver: String?,
    var body: String? = null,
    var type: MessageType = MessageType.Text,
    var status: MessageStatus = MessageStatus.sending,
    val createdAt: Long = System.currentTimeMillis()
) {

    companion object {
        fun ChatItem.getCreatedAt(): Date {
            return createdAt.formatTo()
        }
    }
}
