package com.exquisite.a_mobile_kmm.feature.employee.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class GetAgentBookingResponseDto(
    val data: List<AgentBookingDto>? = null,
    val responseMessage: String? = null,
    val responseCode: String? = null
)

@Serializable
data class AgentBookingDto(
    val id: Int? = null,
    val bookingType: String? = null,
    val bookingDescription: String? = null,
    val paymentStatus: String? = null,
    val serviceStatus: String? = null,
    val amountPaid: Double? = null,
    val bookingId: Int? = null,
    val assignedAgent: AssignedAgentDto? = null,
    val agentClockInDateTime: String? = null,
    val agentClockOutDateTime: String? = null,
    val agentRemark: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

@Serializable
data class AssignedAgentDto(
    val id: Int? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val profilePictureUrl: String? = null,
    val isActive: String? = null,
    val role: RoleDto? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

@Serializable
data class RoleDto(
    val role: String? = null,
    val id: Int? = null,
    val created_at: String? = null,
    val updated_at: String? = null
)
