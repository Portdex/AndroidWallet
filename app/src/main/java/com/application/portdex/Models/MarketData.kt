package com.application.portdex.Models

import com.google.gson.annotations.SerializedName

data class MarketData (
    val status :Status,
    val data : MutableList<DataM>
        )


data class Status(
    val timestamp : String ,
    val error_code: Int
)

data class DataM(
    val id : Int,
    val name : String ,
    val symbol : String,
    val slug : String,
    val quote : Quote,
    val max_supply : Double ,
    val circulating_supply : Double ,
    val total_supply : Double,
    val cmc_rank : Int
)

data class Quote(
    val USD : USD
)

data class USD(
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