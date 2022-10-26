package com.application.portdex.Models

data class PaymentIntentResponse(
    val `data`: PaymentDataRes
)
data class PaymentDataRes(
    val amount: AmountData,
    val amountPaid: AmountPaidB,
    val createDate: String,
    val expiresOn: String,
    val fees: List<FeeB>,
    val id: String,
    val paymentIds: List<Any>,
    val paymentMethods: List<PaymentMethod>,
    val settlementCurrency: String,
    val timeline: List<TimelineB>,
    val updateDate: String
)

data class PaymentMethod(
    val address: String,
    val chain: String,
    val type: String
)

data class TimelineB(
    val context: String,
    val status: String,
    val time: String
)
data class FeeB(
    val amount: String,
    val currency: String,
    val type: String
)

data class AmountPaidB(
    val amount: String,
    val currency: String
)

data class AmountData(
    val amount: String,
    val currency: String
)