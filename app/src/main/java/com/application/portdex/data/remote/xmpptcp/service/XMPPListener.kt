package com.application.portdex.data.remote.xmpptcp.service

import com.application.portdex.domain.models.chat.ChatBody

interface XMPPListener {
    fun onMessageReceived(chat: ChatBody.Builder)
}