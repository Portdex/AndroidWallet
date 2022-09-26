package com.application.portdex.domain.models

import androidx.annotation.Keep

@Keep
data class DigitalDesignItem(
    val id: Int = 0,
    val title: String? = null,
    val thumbnail: String? = null
)
