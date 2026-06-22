package com.exquisite.a_mobile_kmm.feature.wallet.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class InitTopUpAccountRequestDto(
    val customerId: Int,
    val amount: Int
)
