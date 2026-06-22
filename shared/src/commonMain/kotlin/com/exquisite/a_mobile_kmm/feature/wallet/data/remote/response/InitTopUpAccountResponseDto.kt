package com.exquisite.a_mobile_kmm.feature.wallet.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable

@Serializable
data class TopUpDataDto(
    val ref: String = "",
    val paymentLink: String = ""
)

@Serializable
data class InitTopUpAccountResponseDto(
    @Serializable(with = TopUpDataSerializer::class)
    val data: TopUpDataDto? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)

object TopUpDataSerializer :
    EmptyObjectAsNullSerializer<TopUpDataDto>(TopUpDataDto.serializer())
