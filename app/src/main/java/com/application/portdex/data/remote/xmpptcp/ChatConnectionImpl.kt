package com.application.portdex.data.remote.xmpptcp

import com.application.portdex.data.remote.ApiEndPoints
import com.jacopo.pagury.prefs.PrefUtils
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.FlowableOnSubscribe
import io.reactivex.rxjava3.core.Single
import org.jivesoftware.smack.ConnectionConfiguration
import org.jivesoftware.smack.ConnectionListener
import org.jivesoftware.smack.XMPPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import org.jxmpp.jid.impl.JidCreate


class ChatConnectionImpl : ChatConnection {

    private val connectionObserver = FlowableOnSubscribe { emitter ->
        try {
            val serviceName = JidCreate.domainBareFrom(ApiEndPoints.CHAT_DOMAIN)
            val config = XMPPTCPConnectionConfiguration.builder()
                .setUsernameAndPassword(PrefUtils.getChatId(), PrefUtils.getUserName())
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .setXmppDomain(serviceName).setHost(ApiEndPoints.CHAT_HOST)
                .setPort(ApiEndPoints.CHAT_PORT).build()

            val connection = XMPPTCPConnection(config)
            connection.connect()
            connection.login()

            connection.addConnectionListener(object : ConnectionListener {
                override fun connected(connection: XMPPConnection) {
                    emitter.onNext(ConnectionState.Connected)
                    emitter.onComplete()
                }

                override fun authenticated(connection: XMPPConnection?, resumed: Boolean) {
                    emitter.onNext(ConnectionState.Authenticated)
                    emitter.onComplete()
                }

                override fun connectionClosed() {
                    emitter.onNext(ConnectionState.Lost)
                    emitter.onComplete()
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            emitter.onError(e)
        }
    }

    override fun connect(): Single<Boolean> {
        Flowable.create(connectionObserver, BackpressureStrategy.LATEST)
        return Single.create { emitter ->

        }
    }
}