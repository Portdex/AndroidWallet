package com.application.portdex.domain.models.post

import androidx.annotation.Keep

@Keep
data class CreatePost(
    val phoneNumber: String? = null,
    val userId: String? = null,
    val category: String? = null,
    val subCategory: String? = null,
    val postDescription: String? = null,
    val postType: String? = null,
    val lat: String? = null,
    val long: String? = null,
    val postSubject: String? = null
)
