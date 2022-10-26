package com.application.portdex.Models

data class MasterWalletConfigModel (
    var data : MasterWalletData
        )

data class MasterWalletData(
    var payments : Payments
)

data class Payments(
    var masterWalletId : String
)