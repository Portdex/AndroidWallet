package com.application.portdex.domain.models.store

import android.net.Uri
import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class StoreInfo(
    val storePhone: String? = null,
    var userId: String? = null,
    val storeName: String? = null,
    val storeDescription: String? = null,
    val category: String? = null,
    val subCategory: String? = null,
    val storeAddress: String? = null,
    var storePicUrl: String? = null,
    val lat: String? = null,
    val long: String? = null,
    val country: String? = null,
    val isOfferPeriod: String = "true",
    val signedUpUser: String? = null,
    val imageUri: Uri? = null
) : Parcelable
