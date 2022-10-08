package com.application.portdex.data.repository

import android.util.Log
import com.application.portdex.core.utils.RxUtils.request
import com.application.portdex.data.local.source.ChatDataSource
import com.application.portdex.data.local.source.ThreadDataSource
import com.application.portdex.data.mappers.toChatList
import com.application.portdex.data.mappers.toThreadsList
import com.application.portdex.data.remote.xmpptcp.ChatConnection
import com.application.portdex.data.remote.xmpptcp.ConnectionState
import com.application.portdex.data.remote.xmpptcp.service.XMPPListener
import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.models.ProfileInfo
import com.application.portdex.domain.models.chat.ChatBody
import com.application.portdex.domain.models.chat.ChatItem
import com.application.portdex.domain.models.chat.Threads
import com.application.portdex.domain.repository.ChatRepository
import com.jacopo.pagury.prefs.PrefUtils
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import java.util.*
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val connection: ChatConnection,
    private val threadSource: ThreadDataSource,
    private val dataSource: ChatDataSource
) : ChatRepository, XMPPListener {

    companion object {
        private const val TAG = "ChatRepositoryImpl"
    }

    private val disposable = CompositeDisposable()
    private var state = ConnectionState.Connecting

    private var newMessageListener: ((ProfileInfo, String) -> Unit)? = null
    private var currentUser = PrefUtils.getProfileInfo()?.userId
    private var chatUserId: String? = null

    init {
        connection.setXMPPListener(this)
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

    override fun initChatUser(userId: String) {
        this.chatUserId = userId
    }

    override fun getThreadsList(listener: (Resource<List<Threads>>) -> Unit) {
        disposable.add(
            threadSource.getThreads().request()
                .subscribeBy(onNext = {
                    listener(Resource.Success(it.toThreadsList()))
                }, onError = { listener(Resource.Error(it.message)) })
        )
    }

    override fun getChatList(listener: (Resource<MutableList<ChatItem>>) -> Unit) {
        disposable.add(
            dataSource.getChatList(chatUserId).request()
                .subscribeBy(onNext = { listener(Resource.Success(it.toChatList())) },
                    onError = { listener(Resource.Error(it.message)) })
        )
    }

    override fun sendMessage(chatBody: ChatBody.Builder) {
        chatBody.id(UUID.randomUUID().toString())
        connection.sendMessage(chatBody, chatUserId)

        disposable.add(dataSource.insertItem(chatBody.toEntity()))
        disposable.add(threadSource.insertItem(chatBody.toThread()))

        Log.d(TAG, "sendMessage: ${chatBody.build()}")
    }

    override fun onMessageReceived(chat: ChatBody.Builder) {
        chat.receiver(currentUser)

        chat.getMessage()?.let { message ->
            newMessageListener?.invoke(chat.toProfile(), message)
        }
        disposable.add(dataSource.upsert(chat.toEntity()))
        disposable.add(threadSource.upsert(chat.toThread()))

        Log.d(TAG, "onMessageReceived: ${chat.build()}")
    }

    override fun newMessageListener(listener: (ProfileInfo, String) -> Unit) {
        this.newMessageListener = listener
    }

    override fun cleanChat() {
        disposable.add(dataSource.clean())
    }

    override fun onClear() {
        disposable.clear()
    }


}