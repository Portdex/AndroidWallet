package com.application.portdex.domain.models

import androidx.annotation.Keep
import androidx.documentfile.provider.DocumentFile

@Keep
data class CreateProfileInfo(
    val phoneNo: String? = null,
    val country: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val profiePicUrl: String? = null,
    val category: String? = null,
    val subCategory: String? = null,
    val latitude: String? = null,
    val longitude: String? = null,
    val storeId: String? = null,
    val userToken: String? = null,
    val signedUpUser: String? = null,
    val imageFile: DocumentFile? = null
)
