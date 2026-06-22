package com.exquisite.a_mobile_kmm.feature.booking.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable

@Serializable
data class GetPestControlBookingResponseDto(
    @Serializable(with = PestControlBookingDataSerializer::class)
    val data: PestControlBookingDataDto? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)

object PestControlBookingDataSerializer :
    EmptyObjectAsNullSerializer<PestControlBookingDataDto>(PestControlBookingDataDto.serializer())

@Serializable
data class PestControlBookingDataDto(
    val id: Int? = null,
    val preorder: PreorderDto? = null,
    val address: String? = null,
    val images: String? = null,
    val propertyType: String? = null,
    val isHotFogging: Boolean? = null,
    val serviceDate: String? = null,
    val inspectionDate: String? = null,
    val serviceTime: String? = null,
    val inspectionTime: String? = null,
    val customerOwnVehicle: Boolean? = null,
    val numberOfVehicles: Int? = null,
    val extraNote: String? = null,
    val paymentStatus: String? = null,
    val created_at: String? = null,
    val updated_at: String? = null,
    val assignedAgent: AssignedAgentDto? = null,
    val serviceStatus: String? = null
)

@Serializable
data class PreorderDto(
    val id: Int? = null,
    val numberOfRooms: Int? = null,
    val service: ServiceDto? = null,
    val customerId: Int? = null,
    val uniqueRef: String? = null,
    val amount: Double? = null,
    val created_at: String? = null,
    val updated_at: String? = null
)

@Serializable
data class ServiceDto(
    val id: Int? = null,
    val serviceName: String? = null,
    val basePrice: Double? = null,
    val extraRoomPrice: Double? = null,
    val created_at: String? = null,
    val updated_at: String? = null
)
