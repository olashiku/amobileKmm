package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class CompletePaymentRequestDto(
    val orderRef: String,
    val txnRef: String,
    val customerId: Int
)
