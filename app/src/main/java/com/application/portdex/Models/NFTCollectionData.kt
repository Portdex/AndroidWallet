package com.application.portdex.Models

data class NFTCollectionData (
    val collections : MutableList<Collections>
        )
data class Collections(
        val primary_asset_contracts : List<Any>,
        val traits : Any,
        val stats : Stats,
        val banner_image_url : Any,
        val chat_url : Any,
        val created_date : String,
        val default_to_fiat : Boolean,
        val description : Any,
        val dev_buyer_fee_basis_points: String,
        val dev_seller_fee_basis_points : String ,
        val display_data : DisplayData,
        val external_url : Any,
        val featured : Boolean,
        val featured_image_url : Any,
        val hidden : Boolean,
        val safelist_request_status : String,
        val name : String,
        val only_proxied_transfers : Boolean,
        val opensea_buyer_fee_basis_points : String,
        val opensea_seller_fee_basis_points : String ,
        val require_email : Boolean,
        val slug : String,
        val is_nsfw : Boolean,
        val is_rarity_enabled  : Boolean



)

data class DisplayData(
        val card_display_style : String ,
        val images : Any
)

data class Stats(
        val one_day_volume : Double ,
        val one_day_change : Double ,
        val one_day_sales : Double ,
        val one_day_average_price : Double ,
        val one_day_difference : Double ,
        val seven_day_volume : Double ,
        val seven_day_change : Double ,
        val seven_day_sales : Double ,
        val seven_day_difference : Double ,
        val seven_day_average_price : Double ,
        val thirty_day_volume : Double ,
        val thirty_day_change : Double ,
        val thirty_day_sales : Double ,
        val thirty_day_average_price : Double ,
        val thirty_day_difference : Double ,
        val total_volume : Double ,
        val total_sales : Double ,
        val total_supply : Double ,
        val count : Double ,
        val num_owners : Double ,
        val average_price : Double ,
        val num_reports : Double ,
        val market_cap : Double ,

)


