package com.application.portdex.domain.models.chat

import com.application.portdex.core.enums.MessageType
import com.application.portdex.data.local.chat.MessageEntity
import org.jivesoftware.smack.packet.Message


class ChatBody private constructor(
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
        return ("<body>" + message + "</body>"
                + "<username>" + username + "</username>"
                + "<storeId>" + storeId + "</storeId>"
                + "<image>" + image + "</image>"
                + "<messageType>" + messageType?.type + "</body>"
                + "<_type>" + _type + "</body>")
    }

    data class Builder(
        private var message: String? = null,
        private var username: String? = null,
        private var sender: String? = null,
        private var receiver: String? = null,
        private var storeId: String? = null,
        private var image: String? = null,
        private var messageType: MessageType? = null,
        private var _type: Message.Type? = null
    ) {
        fun message(message: String?) = apply { this.message = message }
        fun userName(username: String?) = apply { this.username = username }
        fun sender(sender: String?) = apply { this.sender = sender }
        fun receiver(receiver: String?) = apply { this.receiver = receiver }
        fun storeId(storeId: String?) = apply { this.storeId = storeId }
        fun image(image: String?) = apply { this.image = image }
        fun messageType(messageType: MessageType) = apply { this.messageType = messageType }
        fun setType(type: Message.Type) = apply { this._type = type }
        fun build() = ChatBody(
            message, username, sender, receiver, storeId, image, messageType, _type
        ).toXml()

        fun toEntity() = MessageEntity(
            userName = username,
            userImage = image,
            sender = sender,
            receiver = receiver,
            body = message,
            type = messageType ?: MessageType.Text
        )
    }
}
