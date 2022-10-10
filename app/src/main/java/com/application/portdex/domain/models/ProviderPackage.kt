package com.application.portdex.domain.models

import androidx.annotation.Keep

@Keep
data class ProviderPackage(
    val packageId: String? = null,
    val icon: String? = null,
    val userId: String? = null,
    val price: String? = null,
    val duration: String? = null,
    val name: String? = null,
    var isCartItem: Boolean = false,
    val createdDateTime: String? = null,
)
