package com.application.portdex.Models

data class ChargesRequestModel(
    val description: String,
    val local_price: LocalPrice,
    val metadata: ChargesMetadata,
    val name: String,
    val pricing_type: String
)

data class LocalPrice(
    val amount: Int,
    val currency: String
)

data class ChargesMetadata(
    val customer_id: String,
    val customer_name: String
)