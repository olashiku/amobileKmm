package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable

@Serializable
data class InitDeepCleaningPaymentResponseDto(
    val responseMessage: String = "",
    val responseCode: String = "",
    @Serializable(with = PaymentDataSerializer::class)
    val data: PaymentDataDto? = null
)

object PaymentDataSerializer :
    EmptyObjectAsNullSerializer<PaymentDataDto>(PaymentDataDto.serializer())

@Serializable
data class PaymentDataDto(
    val paymentLink: String = "",
    val ref: String = "")
