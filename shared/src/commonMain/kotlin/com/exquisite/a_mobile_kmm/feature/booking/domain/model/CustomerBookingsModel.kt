package com.exquisite.a_mobile_kmm.feature.booking.domain.model

import kotlinx.serialization.Serializable

data class CustomerBookingsModel(
    val bookings: List<CustomerBooking>
)

@Serializable
data class CustomerBooking(
    val id: Int,
    val bookingType: String,
    val bookingDescription: String,
    val paymentStatus: String?,
    val serviceStatus: String?,
    val amountPaid: Double?,
    val bookingId: Int,
    val assignedAgent: AssignedAgent?,
    val createdAt: String,
    val updatedAt: String
)

@Serializable
data class AssignedAgent(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val profilePictureUrl: String?,
    val isActive: String,
    val createdAt: String,
    val updatedAt: String
)
