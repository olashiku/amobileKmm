package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
data class FindAllRegionsResponseDto(
    val responseMessage: String = "",
    val responseCode: String = "",
    @Serializable(with = RegionListSerializer::class)
    val data: List<RegionDto>? = null
)

object RegionListSerializer :
    EmptyObjectAsNullSerializer<List<RegionDto>>(ListSerializer(RegionDto.serializer()))

@Serializable
data class RegionDto(
    val id: Int,
    val name: String
)
