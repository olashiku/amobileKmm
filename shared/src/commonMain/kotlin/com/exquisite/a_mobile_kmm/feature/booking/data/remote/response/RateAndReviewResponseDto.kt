package com.exquisite.a_mobile_kmm.feature.booking.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class RateAndReviewResponseDto(
    val responseMessage: String = "",
    val responseCode: String = ""
)
