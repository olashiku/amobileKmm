package com.exquisite.a_mobile_kmm.feature.employee.domain.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.feature.employee.data.remote.request.UpdateAgentBookingRequestDto
import com.exquisite.a_mobile_kmm.feature.employee.data.remote.response.GetAgentBookingResponseDto
import com.exquisite.a_mobile_kmm.feature.employee.data.remote.response.GetAgentServiceCountsResponseDto
import com.exquisite.a_mobile_kmm.feature.employee.data.remote.response.UpdateAgentBookingResponseDto
import kotlinx.coroutines.flow.Flow

interface EmployeeRepository {
    suspend fun getAgentServiceCounts(agentId: Int): Flow<Result<GetAgentServiceCountsResponseDto>>
    suspend fun updateAgentBooking(request: UpdateAgentBookingRequestDto): Flow<Result<UpdateAgentBookingResponseDto>>
    suspend fun getAgentBookings(
        agentId: Int,
        pageNo: Int,
        pageSize: Int,
        bookingType: String
    ): Flow<Result<GetAgentBookingResponseDto>>
}
