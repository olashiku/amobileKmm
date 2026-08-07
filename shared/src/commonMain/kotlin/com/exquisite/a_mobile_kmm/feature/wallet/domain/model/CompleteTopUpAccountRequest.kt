package com.exquisite.a_mobile_kmm.feature.wallet.domain.model

data class CompleteTopUpAccountRequest(
    val customerId: Int,
    val ref: String,
    val txnRef: String
)
