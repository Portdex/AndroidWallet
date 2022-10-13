package com.application.portdex.domain.models

import android.net.Uri
import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class CreateProfileInfo(
    val phoneNo: String? = null,
    var country: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    var profiePicUrl: String? = null,
    var category: String? = null,
    var subCategory: String? = null,
    var latitude: String? = null,
    var longitude: String? = null,
    val storeId: String? = null,
    val userToken: String? = null,
    val signedUpUser: String? = null,
    val imageUri: Uri? = null
) : Parcelable
