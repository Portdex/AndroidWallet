package com.application.portdex.data.remote.xmpptcp

import io.reactivex.rxjava3.core.Single

interface ChatConnection {

    fun connect(): Single<Boolean>
}