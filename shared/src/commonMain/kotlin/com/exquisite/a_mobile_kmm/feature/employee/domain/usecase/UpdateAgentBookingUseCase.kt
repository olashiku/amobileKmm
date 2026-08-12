package com.exquisite.a_mobile_kmm.feature.employee.domain.usecase

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.feature.employee.data.mapper.toDomainModel
import com.exquisite.a_mobile_kmm.feature.employee.data.mapper.toDto
import com.exquisite.a_mobile_kmm.feature.employee.domain.model.UpdateAgentBookingRequest
import com.exquisite.a_mobile_kmm.feature.employee.domain.model.UpdateAgentBookingResult
import com.exquisite.a_mobile_kmm.feature.employee.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UpdateAgentBookingUseCase(
    private val repository: EmployeeRepository
) {
    suspend operator fun invoke(request: UpdateAgentBookingRequest): Flow<Result<UpdateAgentBookingResult?>> {
        return repository.updateAgentBooking(request.toDto()).map { result ->
            when (result) {
                is Result.Success -> {
                    val domainModel = result.data.toDomainModel()
                    Result.Success(domainModel)
                }
                is Result.Exception -> Result.Exception(result.exception)
            }
        }
    }
}
