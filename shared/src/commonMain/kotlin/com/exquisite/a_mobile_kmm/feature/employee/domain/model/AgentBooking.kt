package com.exquisite.a_mobile_kmm.feature.employee.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AgentBooking(
    val id: Int,
    val bookingType: String,
    val bookingDescription: String,
    val paymentStatus: String,
    val serviceStatus: String,
    val amountPaid: Double,
    val bookingId: Int,
    val assignedAgent: AssignedAgent,
    val agentClockInDateTime: String?,
    val agentClockOutDateTime: String?,
    val agentRemark: String?,
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
    val profilePictureUrl: String,
    val isActive: String,
    val role: AgentRole,
    val createdAt: String,
    val updatedAt: String
) {
    val fullName: String
        get() = "$firstName $lastName"
}

@Serializable
data class AgentRole(
    val role: String,
    val id: Int,
    val createdAt: String,
    val updatedAt: String
)
