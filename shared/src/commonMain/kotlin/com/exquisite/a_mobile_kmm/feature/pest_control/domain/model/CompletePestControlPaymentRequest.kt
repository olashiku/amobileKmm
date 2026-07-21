package com.exquisite.a_mobile_kmm.feature.pest_control.domain.model

data class CompletePestControlPaymentRequest(
    val customerId: Int,
    val ref: String,
    val txnRef: String
)
