package com.application.portdex.data.mappers

import com.application.portdex.data.remote.dto.ProfileByCategoryDto
import com.application.portdex.data.remote.dto.ProvidePackagesDto
import com.application.portdex.data.remote.dto.ServiceProviderDto
import com.application.portdex.domain.models.ProviderInfo
import com.application.portdex.domain.models.ProviderPackage


fun ServiceProviderDto.mapToProviderInfo(): List<ProviderInfo> {
    return map {
        ProviderInfo(
            groupId = it.groupId,
            storeId = it.storeId,
            firstName = it.firstName,
            subCategory = it.subCategory,
            profilePicUrl = it.profilePicUrl,
            userId = it.userId,
            phoneNumber = it.PhoneNumber,
            category = it.category,
            latitude = it.latitude,
            longitude = it.longitude,
            rating = "4.5 (76+ ratings)"
        )
    }
}

fun ProfileByCategoryDto.mapToProviders(): List<ProviderInfo> {
    return UserProfile.map {
        ProviderInfo(
            groupId = it.groupId,
            storeId = it.storeId,
            firstName = it.firstName,
            subCategory = it.subCategory,
            profilePicUrl = it.profilePicUrl,
            userId = it.userId,
            phoneNumber = it.PhoneNumber,
            category = it.category,
            latitude = it.latitude,
            longitude = it.longitude,
            rating = "4.5 (76+ ratings)"
        )
    }
}

fun ProvidePackagesDto.toProviderPackages(): List<ProviderPackage> {
    return map {
        ProviderPackage(
            packageId = it.packageId,
            icon = it.icon,
            userId = it.userId,
            price = it.price,
            duration = it.duration,
            name = it.name,
            createdDateTime = it.createdDateTime
        )
    }
}