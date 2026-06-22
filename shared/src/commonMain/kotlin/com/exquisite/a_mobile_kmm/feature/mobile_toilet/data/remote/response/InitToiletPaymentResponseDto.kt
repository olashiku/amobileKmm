package com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable

@Serializable
data class InitToiletPaymentResponseDto(
    @Serializable(with = ToiletPaymentDataSerializer::class)
    val data: ToiletPaymentDataDto? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)

object ToiletPaymentDataSerializer :
    EmptyObjectAsNullSerializer<ToiletPaymentDataDto>(ToiletPaymentDataDto.serializer())

@Serializable
data class ToiletPaymentDataDto(
    val first: String = "",
    val second: String = ""
)
