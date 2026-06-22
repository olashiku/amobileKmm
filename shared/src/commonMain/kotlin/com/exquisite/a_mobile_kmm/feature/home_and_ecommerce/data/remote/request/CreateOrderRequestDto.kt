package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateOrderRequestDto(
    val customerId: Int,
    val addressId: Int,
    val products: List<OrderProductDto>
)

@Serializable
data class OrderProductDto(
    val productId: String,
    val quantity: String
)
