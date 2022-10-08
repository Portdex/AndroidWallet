package com.application.portdex.data.remote.xmpptcp

import com.application.portdex.data.remote.xmpptcp.service.XMPPListener
import com.application.portdex.domain.models.chat.ChatBody
import io.reactivex.rxjava3.processors.PublishProcessor

interface ChatConnection {

    fun setXMPPListener(listener: XMPPListener)
    fun connect(): PublishProcessor<ConnectionState>
    fun sendMessage(chatBody: ChatBody.Builder, receiver: String?)
    fun onClear()
}