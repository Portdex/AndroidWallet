package com.application.portdex.core.location

import androidx.fragment.app.FragmentActivity
import com.application.portdex.domain.models.LocationInfo

interface LocationPicker {

    fun initLocation(activity: FragmentActivity, listener: ((LocationInfo) -> Unit)? = null)
}