package com.application.portdex.domain.models

import androidx.annotation.Keep

@Keep
data class DoctorItem(
    val id: Int = 0,
    val userName: String? = null,
    val userImage: String? = null,
    val occupation: String? = null
)
