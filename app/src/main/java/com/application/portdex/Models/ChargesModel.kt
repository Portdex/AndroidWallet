package com.application.portdex.Models

import com.google.gson.annotations.SerializedName

data class ChargesModel(
    val `data`: ChargesData
)

data class ChargesData(
    val addresses: WalletAddresses,
    val brand_color: String,
    val brand_logo_url: String,
    val code: String,
    val created_at: String,
    val description: String,
    val exchange_rates: ExchangeRatesX,
    val expires_at: String,
    val fee_rate: Double,
    val fees_settled: Boolean,
    val hosted_url: String,
    val id: String,
    val local_exchange_rates: LocalExchangeRates,
    val logo_url: String,
    val metadata: Metadata,
    val name: String,
    val offchain_eligible: Boolean,
    val organization_name: String,
    val payment_threshold: PaymentThreshold,
    val payments: List<Any>,
    val pricing: Pricing,
    val pricing_type: String,
    val pwcb_only: Boolean,
    val resource: String,
    val support_email: String,
    val timeline: List<TimelineX>,
    val utxo: Boolean
)

data class WalletAddresses(
    val apecoin: String,
    val bitcoin: String,
    val bitcoincash: String,
    val dai: String,
    val dogecoin: String,
    val ethereum: String,
    val litecoin: String,
    val shibainu: String,
    val tether: String,
    val usdc: String
)

data class LocalExchangeRates (

    @SerializedName("ETH-USD"  ) var ETH_USD  : String? = null,
    @SerializedName("BTC-USD"  ) var BTC_USD  : String? = null,
    @SerializedName("LTC-USD"  ) var LTC_USD  : String? = null,
    @SerializedName("DOGE-USD" ) var DOGE_USD : String? = null,
    @SerializedName("BCH-USD"  ) var BCH_USD  : String? = null,
    @SerializedName("USDC-USD" ) var USDC_USD : String? = null,
    @SerializedName("DAI-USD"  ) var DAI_USD  : String? = null,
    @SerializedName("APE-USD"  ) var APE_USD  : String? = null,
    @SerializedName("SHIB-USD" ) var SHIB_USD : String? = null,
    @SerializedName("USDT-USD" ) var USDT_USD : String? = null

)

data class Apecoin(
    val amount: String,
    val currency: String
)

data class Bitcoin(
    val amount: String,
    val currency: String
)

data class Bitcoincash(
    val amount: String,
    val currency: String
)

data class Dogecoin(
    val amount: String,
    val currency: String
)

data class Ethereum(
    val amount: String,
    val currency: String
)

data class Litecoin(
    val amount: String,
    val currency: String
)

data class Local(
    val amount: String,
    val currency: String
)

data class OverpaymentAbsoluteThreshold(
    val amount: String,
    val currency: String
)

data class PaymentThreshold(
    val overpayment_absolute_threshold: OverpaymentAbsoluteThreshold,
    val overpayment_relative_threshold: String,
    val underpayment_absolute_threshold: UnderpaymentAbsoluteThreshold,
    val underpayment_relative_threshold: String
)

data class Pricing(
    val apecoin: Apecoin,
    val bitcoin: Bitcoin,
    val bitcoincash: Bitcoincash,
    val dai: Dai,
    val dogecoin: Dogecoin,
    val ethereum: Ethereum,
    val litecoin: Litecoin,
    val local: Local,
    val shibainu: Shibainu,
    val tether: Tether,
    val usdc: Usdc
)

data class Dai(
    val amount: String,
    val currency: String
)

data class Shibainu(
    val amount: String,
    val currency: String
)

data class Tether(
    val amount: String,
    val currency: String
)

data class TimelineX(
    val status: String,
    val time: String
)

data class UnderpaymentAbsoluteThreshold(
    val amount: String,
    val currency: String
)

data class Usdc(
    val amount: String,
    val currency: String
)
