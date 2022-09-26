package com.application.portdex.domain.models

import androidx.annotation.Keep


@Keep
data class County(
    val name: String? = null,
    val dialCode: String? = null,
    val isoCode: String? = null,
    val flag: String? = null
)