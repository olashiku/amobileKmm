package com.exquisite.a_mobile_kmm.feature.employee.domain.usecase

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.feature.employee.data.mapper.toDomainModel
import com.exquisite.a_mobile_kmm.feature.employee.data.remote.response.GetAgentServiceCountsResponseDto
import com.exquisite.a_mobile_kmm.feature.employee.domain.model.AgentServiceCounts
import com.exquisite.a_mobile_kmm.feature.employee.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAgentServiceCountsUseCase(
    private val repository: EmployeeRepository
) {
    suspend operator fun invoke(agentId: Int): Flow<Result<AgentServiceCounts?>> {
        return repository.getAgentServiceCounts(agentId).map { result ->
            when (result) {
                is Result.Success -> {
                    val domainModel = result.data.data?.toDomainModel()
                    Result.Success(domainModel)
                }
                is Result.Exception -> Result.Exception(result.exception)
            }
        }
    }
}
