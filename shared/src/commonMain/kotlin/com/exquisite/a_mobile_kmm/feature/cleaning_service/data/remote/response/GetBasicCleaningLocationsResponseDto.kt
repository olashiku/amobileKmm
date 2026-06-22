package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
data class GetBasicCleaningLocationsResponseDto(
    val responseMessage: String = "",
    val responseCode: String = "",
    @Serializable(with = BasicCleaningLocationListSerializer::class)
    val data: List<BasicCleaningLocationDto>? = null
)

object BasicCleaningLocationListSerializer :
    EmptyObjectAsNullSerializer<List<BasicCleaningLocationDto>>(ListSerializer(BasicCleaningLocationDto.serializer()))

@Serializable
data class BasicCleaningLocationDto(
    val id: Int,
    val name: String,
    val price: Double
)
