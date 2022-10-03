package com.application.portdex.domain.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class ProfileInfo(
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
    val phoneNumber: String? = null,
    val userId: String? = null,
    val longitude: String? = null,
    val category: String? = null,
    val latitude: String? = null,
    val createdDateTime: String? = null
) : Parcelable
