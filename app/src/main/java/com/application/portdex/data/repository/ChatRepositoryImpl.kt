package com.application.portdex.data.repository

import com.application.portdex.data.remote.xmpptcp.ChatConnection
import com.application.portdex.domain.repository.ChatRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val connection: ChatConnection
) : ChatRepository {


    override fun connect(): Single<Boolean> {
        return connection.connect()
    }
}