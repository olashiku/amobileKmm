package com.exquisite.a_mobile_kmm.feature.employee.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class UpdateAgentBookingResponseDto(
    val responseMessage: String? = null,
    val responseCode: String? = null
)
