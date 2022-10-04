package com.application.portdex.data.remote.xmpptcp

import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.processors.PublishProcessor
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jivesoftware.smack.ConnectionListener
import org.jivesoftware.smack.ReconnectionManager
import org.jivesoftware.smack.XMPPConnection
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import javax.inject.Inject


class ChatConnectionImpl @Inject constructor(
    private val connection: XMPPTCPConnection
) : ChatConnection {

    companion object {
        private const val TAG = "ChatConnectionImpl"
    }

    private var chatListener: ((ChatManager) -> Unit)? = null
    private val disposable = CompositeDisposable()

    private val connectionFlowable = Flowable.create({ emitter ->
        try {
            connection.connect()
            connection.login()
            Log.d(TAG, "authenticate: ${connection.isAuthenticated}")
            if (connection.isAuthenticated) emitter.onNext(ConnectionState.Authenticated)

            ReconnectionManager.getInstanceFor(connection).apply {
                enableAutomaticReconnection()
            }
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onError = {
                    Log.e(TAG, "connect: ", it)
                }, onNext = { state ->
                    if (state == ConnectionState.Authenticated) initChatManager()
                })
        )
        return connectionPublisher
    }

    private fun initChatManager() {
        ChatManager.getInstanceFor(connection)?.let { chatManager ->
            chatListener?.invoke(chatManager)
        }
    }

    override fun setChatManager(listener: (ChatManager) -> Unit) {
        chatListener = listener
    }

    override fun onClear() {
        disposable.clear()
    }

}