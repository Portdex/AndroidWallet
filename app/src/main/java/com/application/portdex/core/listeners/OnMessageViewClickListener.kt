package com.application.portdex.core.listeners

import android.view.View
import com.application.portdex.domain.models.chat.ChatItem

interface OnMessageViewClickListener {
    fun onMessageViewClick(view: View, message: ChatItem)
}