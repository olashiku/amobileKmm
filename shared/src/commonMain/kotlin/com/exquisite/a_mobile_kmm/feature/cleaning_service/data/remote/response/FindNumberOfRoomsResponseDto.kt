package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
data class FindNumberOfRoomsResponseDto(
    val responseMessage: String = "",
    val responseCode: String = "",
    @Serializable(with = NumberOfRoomsListSerializer::class)
    val data: List<NumberOfRoomsDto>? = null
)

object NumberOfRoomsListSerializer :
    EmptyObjectAsNullSerializer<List<NumberOfRoomsDto>>(ListSerializer(NumberOfRoomsDto.serializer()))

@Serializable
data class NumberOfRoomsDto(
    val id: Int,
    val name: String
)
