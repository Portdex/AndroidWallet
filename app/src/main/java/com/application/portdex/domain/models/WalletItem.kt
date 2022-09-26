package com.application.portdex.domain.models

import androidx.annotation.Keep

@Keep
data class WalletItem(
    val id: Int = 0,
    val label: String? = null,
    val thumbnail: String? = null
)
