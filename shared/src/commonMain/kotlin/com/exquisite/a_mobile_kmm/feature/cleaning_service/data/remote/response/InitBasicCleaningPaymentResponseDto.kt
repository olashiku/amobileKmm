package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class InitBasicCleaningPaymentResponseDto(
    val data: InitBasicCleaningPaymentDataDto,
    val responseMessage: String,
    val responseCode: String
)

@Serializable
data class InitBasicCleaningPaymentDataDto(
    val ref: String,
    val paymentLink: String
)
