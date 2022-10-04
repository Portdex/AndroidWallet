package com.application.portdex.data.local.converters

import androidx.room.TypeConverter
import com.application.portdex.domain.models.chat.MessageStatus

class StatusConverter {

    @TypeConverter
    fun toStatus(value: String) = enumValueOf<MessageStatus>(value)

    @TypeConverter
    fun fromStatus(value: MessageStatus) = value.name
}