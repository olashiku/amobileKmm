package com.exquisite.a_mobile_kmm.feature.address.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class UpdateAddressResponseDto(
    val responseMessage: String = "",
    val responseCode: String = ""
)
