package com.exquisite.a_mobile_kmm.feature.septic.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class CompleteSepticPaymentRequestDto(
    val customerId: Int,
    val ref: String,
    val txnRef: String
)
