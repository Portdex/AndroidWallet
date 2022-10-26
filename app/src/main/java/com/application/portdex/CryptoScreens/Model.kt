package com.inder.cryptocoinbasedemo

import com.google.gson.annotations.SerializedName

data class Model (
    @SerializedName("asset_id")
    val asset_id : String ,
    @SerializedName("name")
    val name : String ,
    @SerializedName("type_is_crypto")
    val type_is_crypto : Int
        )