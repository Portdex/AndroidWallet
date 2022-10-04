package com.application.portdex.di

import com.application.portdex.data.local.source.ChatDataSource
import com.application.portdex.data.remote.ApiEndPoints
import com.application.portdex.data.remote.xmpptcp.ChatConnection
import com.application.portdex.data.remote.xmpptcp.ChatConnectionImpl
import com.application.portdex.data.repository.ChatRepositoryImpl
import com.application.portdex.domain.repository.ChatRepository
import com.jacopo.pagury.prefs.PrefUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import org.jivesoftware.smack.ConnectionConfiguration
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import org.jxmpp.jid.impl.JidCreate

@InstallIn(ViewModelComponent::class)
@Module
class ChatModule {

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

    @Provides
    fun provideChatConnection(xmppConnection: XMPPTCPConnection): ChatConnection {
        return ChatConnectionImpl(xmppConnection)
    }

    @Provides
    @ViewModelScoped
    fun provideChatRepository(
        connection: ChatConnection,
        chatDataSource: ChatDataSource
    ): ChatRepository {
        return ChatRepositoryImpl(connection, chatDataSource)
    }
}