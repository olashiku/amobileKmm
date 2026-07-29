package com.exquisite.a_mobile_kmm.feature.septic.domain.model

data class CompleteSepticPaymentRequest(
    val customerId: Int,
    val ref: String,
    val txnRef: String
)
