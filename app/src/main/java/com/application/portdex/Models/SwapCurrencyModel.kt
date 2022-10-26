package com.application.portdex.Models
class SwapCurrencyModel : ArrayList<SwapCurrencyItem>()

data class SwapCurrencyItem(
    val name: String,
    val network: String,
    val smartContract: String,
    val ticker: String
)
