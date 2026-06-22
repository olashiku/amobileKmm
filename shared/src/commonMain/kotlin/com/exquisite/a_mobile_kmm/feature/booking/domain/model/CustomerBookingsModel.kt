package com.exquisite.a_mobile_kmm.feature.booking.domain.model

data class CustomerBookingsModel(
    val bookings: List<CustomerBooking>
)

data class CustomerBooking(
    val id: Int,
    val bookingType: String,
    val bookingDescription: String,
    val paymentStatus: String?,
    val serviceStatus: String?,
    val amountPaid: Double?,
    val bookingId: Int,
    val createdAt: String,
    val updatedAt: String
)
