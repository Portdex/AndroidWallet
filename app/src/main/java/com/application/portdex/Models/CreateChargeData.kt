package com.application.portdex.Models

import com.google.gson.annotations.SerializedName

data class CreateChargeData (
    val data : ChargeData
        )

data class ChargeData(
    val addresses: Addresses,
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
    val local_exchange_rates: ExchangeRatesX,
    val logo_url: String,
    val metadata: Metadata,
    val name: String,
    val offchain_eligible: Boolean,
    val organization_name: String,
    val payments: List<Any>,
    val pricing_type: String,
    val pwcb_only: Boolean,
    val resource: String,
    val support_email: String,
    val timeline: List<Timeline>,
    val utxo: Boolean
)

data class Addresses(
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

//data class ChargeData(
//    val id : Int,
//    val name : String ,
//    val symbol : String,
//    val slug : String,
//    val quote : ChargeQuote,
//    val max_supply : Double ,
//    val circulating_supply : Double ,
//    val total_supply : Double,
//    val cmc_rank : Int
//)

data class ExchangeRatesX(
    @SerializedName("APE-USD")
    val APE_USD: String,
    @SerializedName("BCH-USD")
    val BCH_USD: String,
    @SerializedName("BTC-USD")
    val BTC_USD: String,
    @SerializedName("DAI-USD")
    val DAI_USD: String,
    @SerializedName("DOGE-USD")
    val DOGE_USD: String,
    @SerializedName("ETH-USD")
    val ETH_USD: String,
    @SerializedName("LTC-USD")
    val LTC_USD: String,
    @SerializedName("SHIB-USD")
    val SHIB_USD: String,
    @SerializedName("USDC-USD")
    val USDC_USD: String,
    @SerializedName("USDT-USD")
    val USDT_USD: String
)

data class Metadata(
    val customer_id: String,
    val customer_name: String
)

data class ChargeQuote(
    val USD : ChargeUSD
)

data class ChargeUSD(
    val price : Double,
    val volume_change_24h : Double,
    val percent_change_1h: Double,
    val percent_change_24h: Double,
    val percent_change_7d: Double,
    val percent_change_30d: Double,
    val percent_change_60d: Double,
    val percent_change_90d: Double,
    val market_cap: Double,
    val market_cap_dominance: Double ,
    val fully_diluted_market_cap : Double,
    val last_updated: String

)