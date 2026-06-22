package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
data class FindApartmentTypeResponseDto(
    val responseMessage: String = "",
    val responseCode: String = "",
    @Serializable(with = ApartmentTypeListSerializer::class)
    val data: List<ApartmentTypeDto>? = null
)

object ApartmentTypeListSerializer :
    EmptyObjectAsNullSerializer<List<ApartmentTypeDto>>(ListSerializer(ApartmentTypeDto.serializer()))

@Serializable
data class ApartmentTypeDto(
    val id: Int,
    val name: String
)
