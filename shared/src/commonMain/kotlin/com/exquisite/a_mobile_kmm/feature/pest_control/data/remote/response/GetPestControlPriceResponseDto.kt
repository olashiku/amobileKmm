package com.exquisite.a_mobile_kmm.feature.pest_control.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable

@Serializable
data class GetPestControlPriceResponseDto(
    @Serializable(with = PestControlPriceDataSerializer::class)
    val data: PestControlPriceDataDto? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)

object PestControlPriceDataSerializer :
    EmptyObjectAsNullSerializer<PestControlPriceDataDto>(PestControlPriceDataDto.serializer())

@Serializable
data class PestControlPriceDataDto(
    val amount: Double,
    val uniqueRef:String
)
