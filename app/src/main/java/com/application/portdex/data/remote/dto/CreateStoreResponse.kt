package com.application.portdex.data.remote.dto
import androidx.annotation.Keep


@Keep
data class CreateStoreResponse(
    val message: String? = null,
    val storeId: String? = null
)