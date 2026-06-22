package com.exquisite.a_mobile_kmm.feature.pest_control.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class CompletePestControlPaymentRequestDto(
    val customerId: Int,
    val ref: String,
    val txnRef: String
)
