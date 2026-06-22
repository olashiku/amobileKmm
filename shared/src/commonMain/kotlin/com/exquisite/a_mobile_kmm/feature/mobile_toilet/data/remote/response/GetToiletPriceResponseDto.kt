package com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable

@Serializable
data class GetToiletPriceResponseDto(
    @Serializable(with = ToiletPriceDataSerializer::class)
    val data: ToiletPriceDataDto? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)

object ToiletPriceDataSerializer :
    EmptyObjectAsNullSerializer<ToiletPriceDataDto>(ToiletPriceDataDto.serializer())

@Serializable
data class ToiletPriceDataDto(
    val numberOfDays: Int,
    val discountGiven: Double,
    val overnight: Double,
    val totalNumberOfGuests: Int,
    val totalAmount: Double,
    val recommendedNumberOfStandardToilets: Int,
    val recommendedNumberOfVipToilets: Int,
    val uniqueRef: String
)
