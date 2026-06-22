package com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class DebitFromAccountResponseDto(
    val responseMessage: String = "",
    val responseCode: String = ""
)
