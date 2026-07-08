package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model

data class CreateOrderRequest(
    val customerId: Int,
    val addressId: Int,
    val products: List<OrderProduct>
)

data class OrderProduct(
    val productId: Int,
    val quantity: Int
)
