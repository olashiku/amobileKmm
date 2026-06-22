package com.exquisite.a_mobile_kmm.feature.janitorial.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class CreateJanitorialResponseDto(
    val responseMessage: String = "",
    val responseCode: String = ""
)
