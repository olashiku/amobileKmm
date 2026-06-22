package com.exquisite.a_mobile_kmm.feature.wallet.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class CompleteTopUpAccountRequestDto(
    val customerId: Int,
    val ref: String,
    val txnRef: String
)
