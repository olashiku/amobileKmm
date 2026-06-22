package com.exquisite.a_mobile_kmm.feature.septic.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable

@Serializable
data class InitSepticPaymentResponseDto(
    @Serializable(with = SepticPaymentDataSerializer::class)
    val data: SepticPaymentDataDto? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)

object SepticPaymentDataSerializer :
    EmptyObjectAsNullSerializer<SepticPaymentDataDto>(SepticPaymentDataDto.serializer())

@Serializable
data class SepticPaymentDataDto(
    val first: String = "",
    val second: String = ""
)
