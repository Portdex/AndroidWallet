package com.application.portdex.data.remote.dto

import androidx.annotation.Keep

@Keep
data class CreatePostResponse(
    val message: String? = null,
    val postID: String? = null
)
