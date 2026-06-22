package com.exquisite.a_mobile_kmm.feature.booking.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
data class GetCustomerBookingsResponseDto(
    @Serializable(with = CustomerBookingListSerializer::class)
    val data: List<CustomerBookingDto>? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)

object CustomerBookingListSerializer :
    EmptyObjectAsNullSerializer<List<CustomerBookingDto>>(ListSerializer(CustomerBookingDto.serializer()))

@Serializable
data class CustomerBookingDto(
    val id: Int? = null,
    val bookingType: String? = null,
    val bookingDescription: String? = null,
    val paymentStatus: String? = null,
    val serviceStatus: String? = null,
    val amountPaid: Double? = null,
    val bookingId: Int? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
