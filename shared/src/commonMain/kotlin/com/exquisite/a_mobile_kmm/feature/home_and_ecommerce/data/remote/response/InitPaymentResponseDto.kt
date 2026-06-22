package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class InitPaymentResponseDto(
    val data: String? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)
