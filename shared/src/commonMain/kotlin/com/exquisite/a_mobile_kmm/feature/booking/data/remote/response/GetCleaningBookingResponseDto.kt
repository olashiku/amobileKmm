package com.exquisite.a_mobile_kmm.feature.booking.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable

@Serializable
data class GetCleaningBookingResponseDto(
    @Serializable(with = CleaningBookingDataSerializer::class)
    val data: CleaningBookingDataDto? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)

object CleaningBookingDataSerializer :
    EmptyObjectAsNullSerializer<CleaningBookingDataDto>(CleaningBookingDataDto.serializer())

@Serializable
data class CleaningBookingDataDto(
    val id: Int? = null,
    val region: RegionDto? = null,
    val location: LocationDto? = null,
    val apartmentType: ApartmentTypeDto? = null,
    val cleaningType: CleaningTypeDto? = null,
    val numberOfRooms: NumberOfRoomsDto? = null,
    val amount: Double? = null,
    val address: String? = null,
    val serviceType: String? = null,
    val customerImages: String? = null,
    val employeeImages: String? = null,
    val assignedAgent: AssignedAgentDto? = null,
    val paymentStatus: String? = null,
    val serviceStatus: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

@Serializable
data class RegionDto(
    val id: Int? = null,
    val name: String? = null
)

@Serializable
data class LocationDto(
    val id: Int? = null,
    val name: String? = null
)

@Serializable
data class ApartmentTypeDto(
    val name: String? = null,
    val id: Int? = null
)

@Serializable
data class CleaningTypeDto(
    val id: Int? = null,
    val name: String? = null
)

@Serializable
data class NumberOfRoomsDto(
    val id: Int? = null,
    val name: String? = null
)

@Serializable
class AssignedAgentDto
