package com.application.portdex.Models

data class CurrencyConvert(
    val adapter: String,
    val amountFrom: Double,
    val amountTo: Double,
    val from: String,
    val fromNetwork: String,
    val maxAmount: Any,
    val minAmount: Double,
    val quotaId: String,
    val to: String,
    val toNetwork: String,
    val validUntil: String
)