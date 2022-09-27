package com.application.portdex.data.mappers

import com.application.portdex.data.remote.dto.ProfileInfoDto
import com.application.portdex.domain.models.CreateProfileInfo
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

fun ProfileInfo.toCreateProfile(): CreateProfileInfo {
    return CreateProfileInfo(
        phoneNo = phoneNumber,
        country = country,
        firstName = firstName,
        lastName = lastName,
        email = email,
        profiePicUrl = profilePicUrl,
        category = category,
        subCategory = subCategory,
        latitude = latitude,
        longitude = longitude,
        storeId = storeId,
        userToken = userToken,
        signedUpUser = signedUpUser
    )
}