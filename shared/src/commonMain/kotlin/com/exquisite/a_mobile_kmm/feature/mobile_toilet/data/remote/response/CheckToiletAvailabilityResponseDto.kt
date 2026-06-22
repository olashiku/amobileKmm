package com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable

@Serializable
data class CheckToiletAvailabilityResponseDto(
    @Serializable(with = ToiletAvailabilityDataSerializer::class)
    val data: ToiletAvailabilityDataDto? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)

object ToiletAvailabilityDataSerializer :
    EmptyObjectAsNullSerializer<ToiletAvailabilityDataDto>(ToiletAvailabilityDataDto.serializer())

@Serializable
data class ToiletAvailabilityDataDto(
    val availableStandardToilets: Int,
    val availableVipToilet: Int,
    val canPurchase: Boolean
)
