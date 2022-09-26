package com.application.portdex.data.remote.dto


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CategoryDto(
    val results: Results = Results()
) {

    data class Results(
        @SerializedName("Items") val items: List<Item> = listOf(),
        @SerializedName("Count") val count: Int = 0,
        @SerializedName("ScannedCount") val scannedCount: Int = 0
    ) {

        data class Item(
            val id: String? = null,
            val data: List<Data> = listOf(),
            val title: String? = null
        ) {

            data class Data(
                val subCatID: Int = 0,
                val name: String? = null,
                val image: String? = null
            )
        }
    }
}