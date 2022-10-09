package com.application.portdex.domain.models.store

import androidx.annotation.Keep

@Keep
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
    val isOfferPeriod: String? = null,
    val signedUpUser: String? = null
)
