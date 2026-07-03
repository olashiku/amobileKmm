package com.exquisite.a_mobile_kmm.feature.cart.domain.model

data class CartModel(
    val productId: Int,
    val productName: String,
    val productImage: String,
    val productPrice: Double,
    val quantity: Int
)