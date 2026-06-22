package com.exquisite.a_mobile_kmm.feature.pest_control.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable

@Serializable
data class InitPestControlPaymentResponseDto(
    @Serializable(with = PestControlPaymentDataSerializer::class)
    val data: PestControlPaymentDataDto? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)

object PestControlPaymentDataSerializer :
    EmptyObjectAsNullSerializer<PestControlPaymentDataDto>(PestControlPaymentDataDto.serializer())

@Serializable
data class PestControlPaymentDataDto(
    val first: String = "",
    val second: String = ""
)
