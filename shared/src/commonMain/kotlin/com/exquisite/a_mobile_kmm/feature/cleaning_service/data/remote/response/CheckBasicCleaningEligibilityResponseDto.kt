package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class CheckBasicCleaningEligibilityResponseDto(
    val responseMessage: String = "",
    val responseCode: String = "",
    val data: Boolean = false
)
