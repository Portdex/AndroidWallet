package com.application.portdex.domain.models.category

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class CategoryData(
    val id: Int = 0,
    val name: String? = null,
    val title: String? = null,
    var image: String? = null
) : Parcelable
