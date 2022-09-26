package com.application.portdex.data.remote.dto


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class FeedItemDto(
    @SerializedName("Post") val post: List<PostItem> = emptyList()
) {
    @Keep
    data class PostItem(
        val userType: String? = null,
        val kindofPost: String? = null,
        val storeId: String? = null,
        val postId: String? = null,
        val country: String? = null,
        val subCategory: List<SubCategory> = emptyList(),
        val productDescription: String? = null,
        val userId: String? = null,
        val longitude: String? = null,
        val category: String? = null,
        val latitude: String? = null,
        val createdDateTime: String? = null,
        val userFirstName: String? = null,
        val userProfilePicUrl: String? = null,
        val phoneNumber: String? = null,
        val postType: String? = null,
        val postSubject: String? = null,
        val postDescription: String? = null
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
            val noOfProducts: String? = null,
            val numberofproducts: String? = null
        )
    }
}