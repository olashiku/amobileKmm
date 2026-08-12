package com.exquisite.a_mobile_kmm.feature.employee.data.mapper

import com.exquisite.a_mobile_kmm.feature.employee.data.remote.request.UpdateAgentBookingRequestDto
import com.exquisite.a_mobile_kmm.feature.employee.data.remote.response.AgentBookingDto
import com.exquisite.a_mobile_kmm.feature.employee.data.remote.response.AgentServiceCountsDto
import com.exquisite.a_mobile_kmm.feature.employee.data.remote.response.AssignedAgentDto
import com.exquisite.a_mobile_kmm.feature.employee.data.remote.response.RoleDto
import com.exquisite.a_mobile_kmm.feature.employee.data.remote.response.UpdateAgentBookingResponseDto
import com.exquisite.a_mobile_kmm.feature.employee.domain.model.AgentBooking
import com.exquisite.a_mobile_kmm.feature.employee.domain.model.AgentRole
import com.exquisite.a_mobile_kmm.feature.employee.domain.model.AgentServiceCounts
import com.exquisite.a_mobile_kmm.feature.employee.domain.model.AssignedAgent
import com.exquisite.a_mobile_kmm.feature.employee.domain.model.UpdateAgentBookingRequest
import com.exquisite.a_mobile_kmm.feature.employee.domain.model.UpdateAgentBookingResult

/**
 * Maps AgentServiceCountsDto to AgentServiceCounts domain model
 * Returns null if data is missing or invalid
 */
fun AgentServiceCountsDto.toDomainModel(): AgentServiceCounts? {
    return if (toiletCount != null &&
        pestControlCount != null &&
        basicCleaningCount != null &&
        deepCleaningCount != null &&
        septicRequestCount != null &&
        totalCount != null) {
        AgentServiceCounts(
            toiletCount = toiletCount,
            pestControlCount = pestControlCount,
            basicCleaningCount = basicCleaningCount,
            deepCleaningCount = deepCleaningCount,
            septicRequestCount = septicRequestCount,
            totalCount = totalCount
        )
    } else {
        null
    }
}

/**
 * Maps UpdateAgentBookingRequest domain model to DTO
 */
fun UpdateAgentBookingRequest.toDto(): UpdateAgentBookingRequestDto {
    return UpdateAgentBookingRequestDto(
        employeeId = employeeId,
        bookingId = bookingId,
        updateType = updateType.value,
        agentRemark = agentRemark
    )
}

/**
 * Maps UpdateAgentBookingResponseDto to UpdateAgentBookingResult domain model
 * Returns null if response is invalid
 */
fun UpdateAgentBookingResponseDto.toDomainModel(): UpdateAgentBookingResult? {
    return if (responseMessage != null && responseCode != null) {
        UpdateAgentBookingResult(
            isSuccess = responseCode == "00",
            message = responseMessage
        )
    } else {
        null
    }
}

/**
 * Maps RoleDto to AgentRole domain model
 * Returns null if required fields are missing
 */
fun RoleDto.toDomainModel(): AgentRole? {
    return if (role != null && id != null && created_at != null && updated_at != null) {
        AgentRole(
            role = role,
            id = id,
            createdAt = created_at,
            updatedAt = updated_at
        )
    } else {
        null
    }
}

/**
 * Maps AssignedAgentDto to AssignedAgent domain model
 * Returns null if required fields are missing
 */
fun AssignedAgentDto.toDomainModel(): AssignedAgent? {
    return if (id != null &&
        firstName != null &&
        lastName != null &&
        email != null &&
        phone != null &&
        profilePictureUrl != null &&
        isActive != null &&
        role != null &&
        createdAt != null &&
        updatedAt != null) {
        val domainRole = role.toDomainModel() ?: return null
        AssignedAgent(
            id = id,
            firstName = firstName,
            lastName = lastName,
            email = email,
            phone = phone,
            profilePictureUrl = profilePictureUrl,
            isActive = isActive,
            role = domainRole,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    } else {
        null
    }
}

/**
 * Maps AgentBookingDto to AgentBooking domain model
 * Returns null if required fields are missing
 */
fun AgentBookingDto.toDomainModel(): AgentBooking? {
    return if (id != null &&
        bookingType != null &&
        bookingDescription != null &&
        paymentStatus != null &&
        serviceStatus != null &&
        amountPaid != null &&
        bookingId != null &&
        assignedAgent != null &&
        createdAt != null &&
        updatedAt != null) {
        val domainAgent = assignedAgent.toDomainModel() ?: return null
        AgentBooking(
            id = id,
            bookingType = bookingType,
            bookingDescription = bookingDescription,
            paymentStatus = paymentStatus,
            serviceStatus = serviceStatus,
            amountPaid = amountPaid,
            bookingId = bookingId,
            assignedAgent = domainAgent,
            agentClockInDateTime = agentClockInDateTime,
            agentClockOutDateTime = agentClockOutDateTime,
            agentRemark = agentRemark,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    } else {
        null
    }
}
