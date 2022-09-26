package com.application.portdex.domain.models

import androidx.annotation.Keep

@Keep
data class PropertyItem(
    val id: Int? = 0,
    val userName: String? = null,
    val designation: String? = null,
    val thumbnail: String? = null,
    val isActive: Boolean = false,
    val isFavorite: Boolean = false
)
