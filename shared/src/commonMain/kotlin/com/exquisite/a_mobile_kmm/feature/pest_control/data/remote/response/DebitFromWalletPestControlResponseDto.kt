package com.exquisite.a_mobile_kmm.feature.pest_control.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class DebitFromWalletPestControlResponseDto(
    val responseMessage: String = "",
    val responseCode: String = ""
)
