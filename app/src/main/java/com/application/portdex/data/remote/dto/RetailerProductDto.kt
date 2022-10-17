package com.application.portdex.data.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class RetailerProductDto(
    @SerializedName("Product")
    val product: List<Product> = emptyList()
) {
    @Keep
    data class Product(
        val productPostId: String? = null,
        val userType: String? = null,
        val storeId: String? = null,
        val kindofPost: String? = null,
        val country: String? = null,
        val city: String? = null,
        val subCategory: List<SubCategory?>? = null,
        val productDescription: String? = null,
        val productSubCategory: String? = null,
        val userId: String? = null,
        val longitude: String? = null,
        val productCategory: String? = null,
        val category: String? = null,
        val latitude: String? = null,
        val createdDateTime: String? = null
    ) {
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
    }
}