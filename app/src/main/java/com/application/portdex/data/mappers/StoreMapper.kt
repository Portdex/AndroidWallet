package com.application.portdex.data.mappers

import com.application.portdex.data.local.cart.CartEntity
import com.application.portdex.domain.models.ProviderPackage


fun List<CartEntity>.toPackageList(): List<ProviderPackage> {
    return map {
        ProviderPackage(
            packageId = it.packageId,
            icon = it.icon,
            userId = it.userId,
            price = it.price,
            duration = it.duration,
            name = it.name,
            isCartItem = true,
            createdDateTime = it.createdDateTime
        )
    }
}

fun ProviderPackage.toCartItem(): CartEntity {
    return CartEntity(
        packageId = packageId,
        icon = icon,
        userId = userId,
        price = price,
        duration = duration,
        name = name,
        createdDateTime = createdDateTime
    )
}
