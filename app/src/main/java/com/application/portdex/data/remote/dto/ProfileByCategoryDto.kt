package com.application.portdex.data.remote.dto

import androidx.annotation.Keep

@Keep
data class ProfileByCategoryDto(
    val UserProfile: List<ServiceProviderDto.ServiceProviderDtoItem> = emptyList()
)
