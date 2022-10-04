package com.application.portdex.data.local.converters

import androidx.room.TypeConverter
import com.application.portdex.core.enums.MessageType

class MessageTypeConverter {

    @TypeConverter
    fun toType(value: String) = enumValueOf<MessageType>(value)

    @TypeConverter
    fun fromType(value: MessageType) = value.name
}