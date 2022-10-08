package com.application.portdex.core.notification

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotificationInfo(
    var key: String,
    var title: String?,
    var body: String?,
) : Parcelable
