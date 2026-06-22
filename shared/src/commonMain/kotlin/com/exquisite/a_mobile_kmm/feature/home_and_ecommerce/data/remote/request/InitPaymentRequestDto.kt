package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class InitPaymentRequestDto(
    val orderRef: String,
    val requestToken: String,
    val courierId: String,
    val serviceCode: String,
    val shippingAmount: String
)
