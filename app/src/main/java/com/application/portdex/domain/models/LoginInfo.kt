package com.application.portdex.domain.models

import androidx.annotation.Keep

@Keep
data class LoginInfo(
    var number: String? = null,
    var accessToken: String? = null,
    var refreshToken: String? = null,
    var idToken: String? = null
)
