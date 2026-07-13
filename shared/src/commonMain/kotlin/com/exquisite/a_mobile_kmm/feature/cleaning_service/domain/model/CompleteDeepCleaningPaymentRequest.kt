package com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model

data class CompleteDeepCleaningPaymentRequest(
    val customerId: Int,
    val ref: String,
    val txnRef: String
)
