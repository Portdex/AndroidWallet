package com.application.portdex.Models

data class CurrencyData (
    var data : List<CurrData>
        )

data class CurrData(
    var id : String ,
    var name : String,
    var min_size : String
)