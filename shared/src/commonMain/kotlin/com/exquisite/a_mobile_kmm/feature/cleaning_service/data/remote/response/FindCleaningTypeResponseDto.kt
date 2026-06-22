package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
data class FindCleaningTypeResponseDto(
    val responseMessage: String = "",
    val responseCode: String = "",
    @Serializable(with = CleaningTypeListSerializer::class)
    val data: List<CleaningTypeDto>? = null
)

object CleaningTypeListSerializer :
    EmptyObjectAsNullSerializer<List<CleaningTypeDto>>(ListSerializer(CleaningTypeDto.serializer()))

@Serializable
data class CleaningTypeDto(
    val id: Int,
    val name: String
)
