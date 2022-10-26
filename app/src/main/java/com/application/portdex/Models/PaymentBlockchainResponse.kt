package com.application.portdex.Models

data class PaymentBlockchainResponse(
    val `data`: List<Data>
)

data class Data(
    val amount: AmountB,
    val amountPaid: AmountPaid,
    val createDate: String,
    val expiresOn: String,
    val fees: List<Fee>,
    val id: String,
    val paymentIds: List<Any>,
    val paymentMethods: List<PaymentMethodB>,
    val settlementCurrency: String,
    val timeline: List<Timeline>,
    val updateDate: String
)

data class AmountB(
    val amount: String,
    val currency: String
)

data class AmountPaid(
    val amount: String,
    val currency: String
)

data class Fee(
    val amount: String,
    val currency: String,
    val type: String
)

data class Timeline(
    val context: String,
    val status: String,
    val time: String
)

data class PaymentMethodB(
    val address: String,
    val chain: String,
    val type: String
)