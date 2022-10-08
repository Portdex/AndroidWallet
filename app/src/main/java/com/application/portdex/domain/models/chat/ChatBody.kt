package com.application.portdex.domain.models.chat

import com.application.portdex.core.enums.MessageType
import com.application.portdex.data.local.chat.MessageEntity
import com.application.portdex.data.local.chat.ThreadEntity
import com.application.portdex.domain.models.ProfileInfo
import org.jivesoftware.smack.packet.Message


class ChatBody private constructor(
    val messageId: String? = null,
    val message: String? = null,
    val username: String? = null,
    val sender: String? = null,
    val receiver: String? = null,
    val storeId: String? = null,
    val image: String? = null,
    val messageType: MessageType? = MessageType.Text,
    val _type: Message.Type? = Message.Type.chat
) {

    private fun toXml(): String {
        return ("<id>" + messageId + "</id>"
                + "<body>" + message + "</body>"
                + "<username>" + username + "</username>"
                + "<storeId>" + storeId + "</storeId>"
                + "<image>" + image + "</image>"
                + "<messageType>" + messageType?.type + "</body>"
                + "<_type>" + _type + "</body>")
    }

    data class Builder(
        private var messageId: String? = null,
        private var message: String? = null,
        private var username: String? = null,
        private var sender: String? = null,
        private var receiver: String? = null,
        private var storeId: String? = null,
        private var chatUserId: String? = null,
        private var image: String? = null,
        private var messageType: MessageType? = null,
        private var _type: Message.Type? = null
    ) {
        fun id(messageId: String?) = apply { this.messageId = messageId }
        fun message(message: String?) = apply { this.message = message }
        fun userName(username: String?) = apply { this.username = username }
        fun sender(sender: String?) = apply { this.sender = sender }
        fun receiver(receiver: String?) = apply { this.receiver = receiver }
        fun chatUserId(chatUserId: String?) = apply { this.chatUserId = chatUserId }
        fun storeId(storeId: String?) = apply { this.storeId = storeId }
        fun image(image: String?) = apply { this.image = image }
        fun messageType(messageType: MessageType) = apply { this.messageType = messageType }
        fun setType(type: Message.Type) = apply { this._type = type }

        fun getMessage() = message

        fun build() = ChatBody(
            messageId, message, username, sender, receiver, storeId, image, messageType, _type
        ).toXml()

        fun toEntity() = MessageEntity(
            messageId = messageId,
            userName = username,
            userImage = image,
            sender = sender,
            receiver = receiver,
            chatUserId = chatUserId,
            body = message,
            type = messageType ?: MessageType.Text
        )

        fun toThread() = ThreadEntity(
            threadId = chatUserId,
            userName = username,
            userImage = image,
            body = message,
            type = messageType ?: MessageType.Text
        )

        fun toProfile() = ProfileInfo(
            userId = sender,
            firstName = username,
            profilePicUrl = image
        )
    }
}
