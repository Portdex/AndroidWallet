package com.application.portdex.domain.models

import androidx.annotation.Keep

@Keep
data class CreateProfileInfo(
    val phoneNo: String? = null,
    var country: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    var profiePicUrl: String? = null,
    val category: String? = null,
    val subCategory: String? = null,
    var latitude: String? = null,
    var longitude: String? = null,
    val storeId: String? = null,
    val userToken: String? = null,
    val signedUpUser: String? = null
)
