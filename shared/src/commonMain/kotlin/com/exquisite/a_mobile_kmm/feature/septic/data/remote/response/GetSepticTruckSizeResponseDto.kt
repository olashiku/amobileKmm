package com.exquisite.a_mobile_kmm.feature.septic.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
data class GetSepticTruckSizeResponseDto(
    @Serializable(with = SepticTruckSizeListSerializer::class)
    val data: List<SepticTruckSizeDto>? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)

object SepticTruckSizeListSerializer :
    EmptyObjectAsNullSerializer<List<SepticTruckSizeDto>>(ListSerializer(SepticTruckSizeDto.serializer()))

@Serializable
data class SepticTruckSizeDto(
    val id: Int,
    val liter: Int,
    val price: Double
)
