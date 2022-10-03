package com.application.portdex.data.local.chat

import androidx.room.PrimaryKey

data class Message(
//    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val sender: String,
    val receiver: String,
    val createdAt: Long
)
