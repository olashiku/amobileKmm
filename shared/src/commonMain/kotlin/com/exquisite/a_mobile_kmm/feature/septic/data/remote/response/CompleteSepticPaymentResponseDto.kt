package com.exquisite.a_mobile_kmm.feature.septic.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class CompleteSepticPaymentResponseDto(
    val responseMessage: String = "",
    val responseCode: String = ""
)
