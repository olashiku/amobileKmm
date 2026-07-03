package com.exquisite.a_mobile_kmm.feature.cart.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Cart",
    indices = [Index(value = ["productId"], unique = true)])
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productId: Int,
    val productName: String,
    val productImage: String,
    val productPrice: Double,
    val quantity: Int
)
