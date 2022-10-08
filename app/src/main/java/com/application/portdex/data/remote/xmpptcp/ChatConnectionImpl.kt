package com.application.portdex.data.remote.xmpptcp

import android.util.Log
import com.application.portdex.core.enums.MessageType
import com.application.portdex.data.remote.ApiEndPoints
import com.application.portdex.data.remote.xmpptcp.service.XMPPListener
import com.application.portdex.domain.models.chat.ChatBody
import com.application.portdex.domain.models.chat.ChatElement
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.processors.PublishProcessor
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jivesoftware.smack.ConnectionListener
import org.jivesoftware.smack.ReconnectionManager
import org.jivesoftware.smack.XMPPConnection
import org.jivesoftware.smack.chat2.Chat
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.chat2.IncomingChatMessageListener
import org.jivesoftware.smack.chat2.OutgoingChatMessageListener
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smack.packet.MessageBuilder
import org.jivesoftware.smack.packet.StandardExtensionElement
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jxmpp.jid.EntityBareJid
import org.jxmpp.jid.impl.JidCreate
import javax.inject.Inject


class ChatConnectionImpl @Inject constructor(
    private val connection: XMPPTCPConnection
) : ChatConnection, OutgoingChatMessageListener, IncomingChatMessageListener {

    companion object {
        private const val TAG = "ChatConnectionImpl"
    }

    private var listener: XMPPListener? = null
    private var chatManager: ChatManager? = null
    private val disposable = CompositeDisposable()

    override fun setXMPPListener(listener: XMPPListener) {
        this.listener = listener
    }

    private val connectionFlowable = Flowable.create({ emitter ->
        try {
            connection.addConnectionListener(object : ConnectionListener {
                override fun connected(connection: XMPPConnection?) {
                    Log.d(TAG, "connected: ")
                    emitter.onNext(ConnectionState.Connected)
                }

                override fun authenticated(connection: XMPPConnection, resumed: Boolean) {
                    Log.d(TAG, "authenticated: ${connection.isAuthenticated}")
                    emitter.onNext(ConnectionState.Authenticated)
                }

                override fun connectionClosedOnError(e: Exception) {
                    Log.e(TAG, "connectionClosedOnError: ", e)
                    emitter.onNext(ConnectionState.Lost)
                }
            })
            connection.connect()
            connection.login()

            ReconnectionManager.getInstanceFor(connection).apply {
                enableAutomaticReconnection()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }, BackpressureStrategy.LATEST)

    private val connectionPublisher = PublishProcessor.create<ConnectionState>()
    override fun connect(): PublishProcessor<ConnectionState> {
        connectionFlowable.subscribeOn(Schedulers.io()).subscribe(connectionPublisher)
        disposable.add(
            connectionPublisher.onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .subscribeBy(onError = {
                    Log.e(TAG, "connect: ", it)
                }, onNext = { state ->
                    Log.d(TAG, "connect: ${state.name}")
                    if (state == ConnectionState.Authenticated) initChatManager()
                })
        )
        return connectionPublisher
    }

    private fun initChatManager() {
        chatManager = ChatManager.getInstanceFor(connection).apply {
            addOutgoingListener(this@ChatConnectionImpl)
            addIncomingListener(this@ChatConnectionImpl)
        }
    }

    override fun newOutgoingMessage(
        to: EntityBareJid?,
        messageBuilder: MessageBuilder,
        chat: Chat?
    ) {
        Log.d(TAG, "newOutgoingMessage: body ${messageBuilder.body}")
        Log.d(TAG, "newOutgoingMessage: from ${messageBuilder.from}")
        Log.d(TAG, "newOutgoingMessage: to ${messageBuilder.to}")
    }

    override fun newIncomingMessage(jid: EntityBareJid?, message: Message, chat: Chat?) {
        Log.d(TAG, "message.getBody(): " + message.body)
        Log.d(TAG, "message.getFrom(): " + message.from)

        val messageId = message.getExtensionValue("id")
        val body = message.getExtensionValue("body")
        val userName = message.getExtensionValue("username")
        val storeId = message.getExtensionValue("storeId")
        val image = message.getExtensionValue("image")
        val messageType = message.getExtensionValue("messageType")?.toInt()

        val from = message.from.toString()
        var fromUser = from.substringBefore("@")
        if (fromUser.isEmpty()) fromUser = from

        Log.d(
            TAG, "newIncomingMessage: Id: $messageId" +
                    "\nFrom: $fromUser" +
                    "\nBody: $body" +
                    "\nName: $userName" +
                    "\nStore: $storeId" +
                    "\nImage: $image" +
                    "\nType: $messageType"
        )

        val chatBody = ChatBody.Builder()
            .id(messageId)
            .message(body)
            .userName(userName)
            .image(image)
            .storeId(storeId)
            .sender(fromUser)
            .chatUserId(fromUser)
            .messageType(MessageType.values().find { it.type == messageType } ?: MessageType.Text)
            .setType(Message.Type.chat)

        listener?.onMessageReceived(chatBody)
    }

    override fun sendMessage(chatBody: ChatBody.Builder, receiver: String?) {
        try {
            val jid = JidCreate.entityBareFrom("${receiver}@${ApiEndPoints.CHAT_DOMAIN}")
            val chat = chatManager?.chatWith(jid)
            val builder = MessageBuilder.buildMessage()
                .ofType(Message.Type.chat)
                .addExtension(ChatElement(chatBody.build()))
                .build()
            chat?.send(Message(builder))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun Message.getExtensionValue(key: String): String? {
        val item = extensions.find { it.elementName == key }
        return if (key == "body" && item is Message.Body) item.message
        else if (item is StandardExtensionElement) item.text else null
    }

    override fun onClear() {
        disposable.clear()
    }


}