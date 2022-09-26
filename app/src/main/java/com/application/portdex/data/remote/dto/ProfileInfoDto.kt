package com.application.portdex.data.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


class ProfileInfoDto : ArrayList<ProfileInfoDto.ProfileInfoItemDto>() {

    @Keep
    data class ProfileInfoItemDto(
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
        @SerializedName("PhoneNumber")
        val phoneNumber: String? = null,
        val userId: String? = null,
        val longitude: String? = null,
        val category: String? = null,
        val latitude: String? = null,
        val createdDateTime: String? = null
    )
}