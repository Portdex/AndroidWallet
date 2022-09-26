package com.application.portdex.domain.models.category


import androidx.annotation.Keep

@Keep
data class SubCategory(
    val offerPrice: String? = null,
    val productImage: String? = null,
    val productId: String? = null,
    val oldPrice: String? = null,
    val qty: String? = null,
    val brand: String? = null,
    val productName: String? = null,
    val noOfProducts: String? = null
)