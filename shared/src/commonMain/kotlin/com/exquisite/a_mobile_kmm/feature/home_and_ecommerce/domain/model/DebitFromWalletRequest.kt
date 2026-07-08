package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model

data class DebitFromWalletRequest(
    val orderRef: String,
    val requestToken: String,
    val courierId: String,
    val serviceCode: String,
    val shippingAmount: String
)
