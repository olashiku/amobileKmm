package com.exquisite.a_mobile_kmm.feature.training.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class EnrollTrainingByAccountBalanceResponseDto(
    val responseMessage: String = "",
    val responseCode: String = ""
)
