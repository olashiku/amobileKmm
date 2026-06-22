package com.exquisite.a_mobile_kmm.feature.auth.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ResendOtpResponseDto(
    val responseMessage: String,
    val responseCode: String
)
