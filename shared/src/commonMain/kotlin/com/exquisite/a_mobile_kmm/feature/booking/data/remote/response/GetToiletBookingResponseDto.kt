package com.exquisite.a_mobile_kmm.feature.booking.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable

@Serializable
data class GetToiletBookingResponseDto(
    @Serializable(with = ToiletBookingDataSerializer::class)
    val data: ToiletBookingDataDto? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)

object ToiletBookingDataSerializer :
    EmptyObjectAsNullSerializer<ToiletBookingDataDto>(ToiletBookingDataDto.serializer())

@Serializable
data class ToiletBookingDataDto(
    val id: Int? = null,
    val numberOfVipToilet: Int? = null,
    val toiletEstimate: ToiletEstimateDto? = null,
    val companyName: String? = null,
    val companyEmail: String? = null,
    val recipientPhoneNumber: String? = null,
    val numberOfStandardToilet: Int? = null,
    val bookingDate: String? = null,
    val startDate: String? = null,
    val startTime: String? = null,
    val endDate: String? = null,
    val endTime: String? = null,
    val isOverNight: Boolean? = null,
    val finishingDate: String? = null,
    val pictureOfEventLocation: String? = null,
    val pictureOfToiletPlacement: String? = null,
    val typeOfEvent: String? = null,
    val extraNote: String? = null,
    val address: String? = null,
    val contactPersonEmail: String? = null,
    val contactPersonName: String? = null,
    val paymentStatus: String? = null,
    val serviceStatus: String? = null,
    val assignedAgent: AssignedAgentDto? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

@Serializable
data class ToiletEstimateDto(
    val id: Int? = null,
    val minimumNumberOfGuest: String? = null,
    val maximumNumberOfGuest: String? = null,
    val serviceType: String? = null,
    val numberOfStandardToilet: String? = null,
    val numberOfVipToilets: String? = null,
    val eventStartDate: String? = null,
    val eventEndDate: String? = null,
    val eventStartTIme: String? = null,
    val eventEndTIme: String? = null,
    val numberOfDays: Int? = null,
    val discountGiven: Double? = null,
    val overnight: Double? = null,
    val totalNumberOfGuests: Int? = null,
    val totalAmount: Double? = null,
    val recommendedNumberOfStandardToilets: Int? = null,
    val recommendedNumberOfVipToilets: Int? = null,
    val uniqueRef: String? = null
)
