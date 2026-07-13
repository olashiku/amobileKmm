package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class CompleteBasicCleaningPaymentRequestDto(
    val customerId: Int,
    val ref: String,
    val txnRef: String
)
