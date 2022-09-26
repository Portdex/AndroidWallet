package com.application.portdex.domain.models.category

import androidx.annotation.Keep

@Keep
data class CategoryData(
    val id: Int = 0,
    val name: String? = null,
    var image: String? = null
)
