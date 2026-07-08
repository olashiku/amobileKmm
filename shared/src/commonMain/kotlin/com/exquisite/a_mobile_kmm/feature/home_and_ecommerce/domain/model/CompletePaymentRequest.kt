package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model

data class CompletePaymentRequest(
    val orderRef: String,
    val txnRef: String,
    val customerId: Int
)
