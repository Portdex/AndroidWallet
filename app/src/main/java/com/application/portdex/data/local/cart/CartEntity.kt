package com.application.portdex.data.local.cart

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "carts", indices = [Index(value = ["packageId"], unique = true)])
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val packageId: String? = null,
    val icon: String? = null,
    val userId: String? = null,
    val price: String? = null,
    val duration: String? = null,
    val name: String? = null,
    val createdDateTime: String? = null,
    val updatedAt: Long = System.currentTimeMillis()
)
