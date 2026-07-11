package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
data class FindLocationByRegionResponseDto(
    val responseMessage: String = "",
    val responseCode: String = "",
    @Serializable(with = LocationListSerializer::class)
    val data: List<LocationDto>? = null
)

object LocationListSerializer :
    EmptyObjectAsNullSerializer<List<LocationDto>>(ListSerializer(LocationDto.serializer()))

@Serializable
data class LocationDto(
    val id: Int,
    val name: String)
