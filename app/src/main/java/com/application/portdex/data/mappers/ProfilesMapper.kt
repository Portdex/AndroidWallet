package com.application.portdex.data.mappers

import com.application.portdex.data.remote.dto.ProfileInfoDto
import com.application.portdex.domain.models.ProfileInfo


fun ProfileInfoDto.ProfileInfoItemDto.mapToProfileInfo(): ProfileInfo {
    return ProfileInfo(
        userToken = userToken,
        lastName = lastName,
        groupId = groupId,
        storeId = storeId,
        email = email,
        country = country,
        firstName = firstName,
        subCategory = subCategory,
        signedUpUser = signedUpUser,
        profilePicUrl = profilePicUrl,
        phoneNumber = phoneNumber,
        userId = userId,
        longitude = longitude,
        category = category,
        latitude = latitude,
        createdDateTime = createdDateTime
    )
}