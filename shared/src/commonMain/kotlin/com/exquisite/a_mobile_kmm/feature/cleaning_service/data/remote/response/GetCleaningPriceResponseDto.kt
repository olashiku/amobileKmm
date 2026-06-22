package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable

@Serializable
data class GetCleaningPriceResponseDto(
    val responseMessage: String = "",
    val responseCode: String = "",
    @Serializable(with = CleaningPriceDataSerializer::class)
    val data: CleaningPriceDto? = null
)

object CleaningPriceDataSerializer :
    EmptyObjectAsNullSerializer<CleaningPriceDto>(CleaningPriceDto.serializer())

@Serializable
data class CleaningPriceDto(
    val price: Double,
    val currency: String = "NGN"
)
