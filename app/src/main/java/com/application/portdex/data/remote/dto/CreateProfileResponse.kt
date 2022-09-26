package com.application.portdex.data.remote.dto
import androidx.annotation.Keep


@Keep
data class CreateProfileResponse(
    val message: String? = null,
    val userId: String? = null
)