package com.exquisite.a_mobile_kmm.feature.cleaners_registration.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class RegisterCleanerResponseDto(
    val responseMessage: String = "",
    val responseCode: String = ""
)
