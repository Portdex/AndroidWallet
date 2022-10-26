package com.application.portdex.Models
import retrofit2.http.Field

class PaymentIntentRequest (
    var idempotencyKey : String ,
    var amount : Amount,
    var settlementCurrency : String ,
    var paymentMethods: MutableList<PaymentMethods>
        )

data class Amount(
    var amount :String ,
    var currency: String
)

data class PaymentMethods(
    var type : String,
    var chain: String
)