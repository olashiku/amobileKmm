package com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
data class GetStandardToiletsListResponseDto(
    @Serializable(with = StandardToiletListSerializer::class)
    val data: List<StandardToiletDto>? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)

object StandardToiletListSerializer :
    EmptyObjectAsNullSerializer<List<StandardToiletDto>>(ListSerializer(StandardToiletDto.serializer()))

@Serializable
data class StandardToiletDto(
    val id: Int,
    val minimum: Int,
    val maximum: Int,
    val quantity: Int
)
