package com.application.portdex.di

import com.application.portdex.data.local.source.ChatDataSource
import com.application.portdex.data.local.source.ThreadDataSource
import com.application.portdex.data.remote.ApiEndPoints
import com.application.portdex.data.remote.xmpptcp.ChatConnection
import com.application.portdex.data.remote.xmpptcp.ChatConnectionImpl
import com.application.portdex.data.repository.ChatRepositoryImpl
import com.application.portdex.domain.repository.ChatRepository
import com.jacopo.pagury.prefs.PrefUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.jivesoftware.smack.ConnectionConfiguration
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import org.jxmpp.jid.impl.JidCreate
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ChatModule {

    @Singleton
    @Provides
    fun provideXMPPTCPConnection(): XMPPTCPConnection {
        val serviceName = JidCreate.domainBareFrom(ApiEndPoints.CHAT_DOMAIN)
        val config = XMPPTCPConnectionConfiguration.builder()
            .setUsernameAndPassword(PrefUtils.getChatId(), PrefUtils.getUserName())
            .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
            .setXmppDomain(serviceName).setHost(ApiEndPoints.CHAT_HOST)
            .setPort(ApiEndPoints.CHAT_PORT).build()
        return XMPPTCPConnection(config)
    }

    @Singleton
    @Provides
    fun provideChatConnection(xmppConnection: XMPPTCPConnection): ChatConnection {
        return ChatConnectionImpl(xmppConnection)
    }

    @Singleton
    @Provides
    fun provideChatRepository(
        connection: ChatConnection,
        threadSource: ThreadDataSource,
        chatDataSource: ChatDataSource
    ): ChatRepository {
        return ChatRepositoryImpl(connection, threadSource, chatDataSource)
    }
}