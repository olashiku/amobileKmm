package com.exquisite.a_mobile_kmm.feature.employee.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateAgentBookingRequestDto(
    val employeeId: Int,
    val bookingId: Int,
    val updateType: String,
    val agentRemark: String
)
