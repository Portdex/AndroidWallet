package com.application.portdex.domain.models

import androidx.annotation.Keep

@Keep
data class DesignersItem(
    val id: Int = 0,
    val userName: String? = null,
    val rating: String? = null,
    val thumbnail: String? = null,
    val userImage: String? = null,
    val isActive: Boolean = false,
    val isFavorite: Boolean = false
)
