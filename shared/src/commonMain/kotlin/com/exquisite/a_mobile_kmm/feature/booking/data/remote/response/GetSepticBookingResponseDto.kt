package com.exquisite.a_mobile_kmm.feature.booking.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable

@Serializable
data class GetSepticBookingResponseDto(
    @Serializable(with = SepticBookingDataSerializer::class)
    val data: SepticBookingDataDto? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)

object SepticBookingDataSerializer :
    EmptyObjectAsNullSerializer<SepticBookingDataDto>(SepticBookingDataDto.serializer())

@Serializable
data class SepticBookingDataDto(
    val id: Int? = null,
    val fullName: String? = null,
    val phoneNo: String? = null,
    val email: String? = null,
    val address: String? = null,
    val dateOfExcavation: String? = null,
    val timeOfExcavation: String? = null,
    val specialNote: String? = null,
    val liter: Int? = null,
    val amount: Double? = null,
    val paymentStatus: String? = null,
    val serviceStatus: String? = null,
    val assignedAgent: AssignedAgentDto? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
