package com.application.portdex.domain.models.products

import androidx.annotation.Keep

@Keep
data class ProductInfo(
    val productPostId: String? = null,
    val userType: String? = null,
    val storeId: String? = null,
    val kindofPost: String? = null,
    val offerPrice: String? = null,
    val productImage: String? = null,
    val productId: String? = null,
    val oldPrice: String? = null,
    val qty: String? = null,
    val noOfProducts: String? = null,
    val productCategory: String? = null,
    val productSubCategory: String? = null,
    val userId: String? = null,
    val category: String? = null,
    val createdDateTime: String? = null
)