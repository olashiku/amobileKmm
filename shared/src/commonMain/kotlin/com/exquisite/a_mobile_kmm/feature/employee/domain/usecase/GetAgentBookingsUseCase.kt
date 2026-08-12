package com.exquisite.a_mobile_kmm.feature.employee.domain.usecase

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.feature.employee.data.mapper.toDomainModel
import com.exquisite.a_mobile_kmm.feature.employee.domain.model.AgentBooking
import com.exquisite.a_mobile_kmm.feature.employee.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAgentBookingsUseCase(
    private val repository: EmployeeRepository
) {
    suspend operator fun invoke(
        agentId: Int,
        pageNo: Int = 0,
        pageSize: Int = 10,
        bookingType: String
    ): Flow<Result<List<AgentBooking>>> {
        return repository.getAgentBookings(agentId, pageNo, pageSize, bookingType).map { result ->
            when (result) {
                is Result.Success -> {
                    val bookings = result.data.data?.mapNotNull { it.toDomainModel() } ?: emptyList()
                    Result.Success(bookings)
                }
                is Result.Exception -> Result.Exception(result.exception)
            }
        }
    }
}
