package com.application.portdex.data.repository

import android.util.Log
import com.application.portdex.core.enums.MessageType
import com.application.portdex.core.utils.RxUtils.request
import com.application.portdex.data.local.source.ChatDataSource
import com.application.portdex.data.mappers.toChatList
import com.application.portdex.data.remote.ApiEndPoints
import com.application.portdex.data.remote.xmpptcp.ChatConnection
import com.application.portdex.data.remote.xmpptcp.ConnectionState
import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.models.chat.ChatBody
import com.application.portdex.domain.models.chat.ChatElement
import com.application.portdex.domain.models.chat.ChatItem
import com.application.portdex.domain.repository.ChatRepository
import com.jacopo.pagury.prefs.PrefUtils
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import org.jivesoftware.smack.chat2.Chat
import org.jivesoftware.smack.chat2.IncomingChatMessageListener
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smack.packet.MessageBuilder
import org.jxmpp.jid.EntityBareJid
import org.jxmpp.jid.impl.JidCreate
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val connection: ChatConnection,
    private val dataSource: ChatDataSource
) : ChatRepository, IncomingChatMessageListener {

    companion object {
        private const val TAG = "ChatRepositoryImpl"
    }

    private val disposable = CompositeDisposable()
    private var state = ConnectionState.Connecting

    private var currentUser = PrefUtils.getProfileInfo()?.userId
    private var chat: Chat? = null

    init {
        initConnection()
    }

    private fun initConnection() {
        disposable.add(
            connection.connect().onBackpressureLatest().request()
                .subscribeBy(onError = {
                    Log.e(TAG, "authenticate: error", it)
                }, onNext = {
                    Log.d(TAG, "authenticate: ${it.name}")
                    state = it
                })
        )
    }

    override fun initChatManager(userId: String) {
        connection.setChatManager { chatManager ->
            try {
                chatManager.addIncomingListener(this)
                val jid = JidCreate.entityBareFrom("${userId}@${ApiEndPoints.CHAT_DOMAIN}")
                chat = chatManager.chatWith(jid)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun getChatList(
        sender: String?,
        receiver: String?,
        listener: (Resource<MutableList<ChatItem>>) -> Unit
    ) {
        disposable.add(
            dataSource.getChatList(sender, receiver).request()
                .subscribeBy(onNext = { listener(Resource.Success(it.toChatList())) },
                    onError = { listener(Resource.Error(it.message)) })
        )
    }

    override fun sendMessage(chatBody: ChatBody.Builder) {
        if (state == ConnectionState.Authenticated) {
            val builder = MessageBuilder.buildMessage()
                .ofType(Message.Type.chat)
                .addExtension(ChatElement(chatBody.build()))
                .build()
            chat?.send(Message(builder))
            Log.d(TAG, "sendMessage: ${chatBody.build()}")
        }
        disposable.add(dataSource.insertItem(chatBody.toEntity()))
    }

    override fun newIncomingMessage(jid: EntityBareJid, message: Message, chat: Chat) {
        Log.d(TAG, "message.getBody() :" + message.body)
        Log.d(TAG, "message.getFrom() :" + message.from)

        val from = message.from.toString()
        val fromUser = if (from.contains("/")) from.split("/").toTypedArray()[0] else from

        val chatBody = ChatBody.Builder()
            .message(message.body)
            .sender(fromUser)
            .receiver(currentUser)
            .messageType(MessageType.Text)
            .setType(Message.Type.chat)
        disposable.add(dataSource.insertItem(chatBody.toEntity()))
    }

    override fun onClear() {
        disposable.clear()
    }

}