package com.application.portdex.data.remote.xmpptcp

import io.reactivex.rxjava3.processors.PublishProcessor
import org.jivesoftware.smack.chat2.ChatManager

interface ChatConnection {

    fun connect(): PublishProcessor<ConnectionState>
    fun setChatManager(listener: (ChatManager) -> Unit)
    fun onClear()
}