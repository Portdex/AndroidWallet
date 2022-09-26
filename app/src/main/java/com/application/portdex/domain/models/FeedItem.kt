package com.application.portdex.domain.models

import androidx.annotation.Keep
import com.application.portdex.domain.models.category.SubCategory
import java.util.*

@Keep
data class FeedItem(
    val userType: String? = null,
    val kindofPost: String? = null,
    val storeId: String? = null,
    val postId: String? = null,
    val country: String? = null,
    val subCategory: List<SubCategory> = emptyList(),
    val productDescription: String? = null,
    val userId: String? = null,
    val category: String? = null,
    val createdDateTime: Date? = null,
    val userFirstName: String? = null,
    val userProfilePicUrl: String? = null,
    val phoneNumber: String? = null,
    val postType: String? = null,
    val postSubject: String? = null,
    val postDescription: String? = null
)
