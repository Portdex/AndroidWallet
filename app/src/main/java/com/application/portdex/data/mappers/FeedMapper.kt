package com.application.portdex.data.mappers

import com.application.portdex.core.utils.FormatUtils.formatTo
import com.application.portdex.data.remote.dto.FeedItemDto
import com.application.portdex.domain.models.FeedItem
import com.application.portdex.domain.models.category.SubCategory


fun FeedItemDto.toFeedItems(): List<FeedItem> {
    return post.map {
        FeedItem(
            userType = it.userType,
            kindofPost = it.kindofPost,
            storeId = it.storeId,
            postId = it.postId,
            country = it.country,
            productDescription = it.postDescription,
            userId = it.userId,
            category = it.category,
            createdDateTime = it.createdDateTime?.formatTo(),
            userFirstName = it.userFirstName,
            userProfilePicUrl = it.userProfilePicUrl,
            phoneNumber = it.phoneNumber,
            postType = it.postType,
            postSubject = it.postSubject,
            postDescription = it.postDescription,
            subCategory = it.subCategory.toSubCategories()
        )
    }
}

fun List<FeedItemDto.PostItem.SubCategory>.toSubCategories(): List<SubCategory> {
    return map {
        SubCategory(
            productImage = it.productImage,
            productId = it.productId,
            oldPrice = it.oldPrice,
            qty = it.qty,
            brand = it.brand,
            productName = it.productName,
            noOfProducts = it.noOfProducts
        )
    }
}