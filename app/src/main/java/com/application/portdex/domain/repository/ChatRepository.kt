package com.application.portdex.domain.repository

import io.reactivex.rxjava3.core.Single

interface ChatRepository {

    fun connect(): Single<Boolean>
}