package com.exquisite.a_mobile_kmm.feature.pest_control.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
data class GetServiceListResponseDto(
    @Serializable(with = ServiceListSerializer::class)
    val data: List<ServiceDto>? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)

object ServiceListSerializer :
    EmptyObjectAsNullSerializer<List<ServiceDto>>(ListSerializer(ServiceDto.serializer()))

@Serializable
data class ServiceDto(
    val id: Int,
    val serviceName: String,
    val basePrice: Double,
    val extraRoomPrice: Double?,
    val created_at: String,
    val updated_at: String
)
