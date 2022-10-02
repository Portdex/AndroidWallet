package com.application.portdex.data.remote.dto


import androidx.annotation.Keep

class ProvidePackagesDto : ArrayList<ProvidePackagesDto.ProvidePackagesDtoItem>() {

    @Keep
    data class ProvidePackagesDtoItem(
        val packageId: String? = null,
        val icon: String? = null,
        val userId: String? = null,
        val price: String? = null,
        val duration: String? = null,
        val name: String? = null,
        val createdDateTime: String? = null
    )
}