package com.application.portdex.domain.models.category

import androidx.annotation.Keep

@Keep
data class CategoryItem(
    val id: String? = null,
    val title: String,
    val data: List<CategoryData> = emptyList()
)
