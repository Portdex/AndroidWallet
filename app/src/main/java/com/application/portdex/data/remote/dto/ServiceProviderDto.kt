package com.application.portdex.data.remote.dto


import androidx.annotation.Keep

class ServiceProviderDto : ArrayList<ServiceProviderDto.ServiceProviderDtoItem>() {
    @Keep
    data class ServiceProviderDtoItem(
        val userToken: String? = null,
        val lastName: String? = null,
        val groupId: String? = null,
        val storeId: String? = null,
        val email: String? = null,
        val country: String? = null,
        val firstName: String? = null,
        val subCategory: String? = null,
        val signedUpUser: String? = null,
        val profilePicUrl: String? = null,
        val userId: String? = null,
        val PhoneNumber: String? = null,
        val longitude: String? = null,
        val category: String? = null,
        val latitude: String? = null,
        val createdDateTime: String? = null
    )
}