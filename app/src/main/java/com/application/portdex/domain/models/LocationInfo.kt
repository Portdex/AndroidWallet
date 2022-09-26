package com.application.portdex.domain.models

import androidx.annotation.Keep

@Keep
data class LocationInfo(
    val latitude: Double,
    val longitude: Double
)
