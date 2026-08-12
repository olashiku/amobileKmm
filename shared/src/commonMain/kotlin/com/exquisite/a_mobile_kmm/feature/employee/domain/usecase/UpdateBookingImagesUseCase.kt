package com.exquisite.a_mobile_kmm.feature.employee.domain.usecase

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.feature.employee.data.mapper.toDomainModel
import com.exquisite.a_mobile_kmm.feature.employee.data.mapper.toDto
import com.exquisite.a_mobile_kmm.feature.employee.domain.model.UpdateBookingImagesRequest
import com.exquisite.a_mobile_kmm.feature.employee.domain.model.UpdateBookingImagesResult
import com.exquisite.a_mobile_kmm.feature.employee.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UpdateBookingImagesUseCase(
    private val repository: EmployeeRepository
) {
    suspend operator fun invoke(request: UpdateBookingImagesRequest): Flow<Result<UpdateBookingImagesResult?>> {
        return repository.updateBookingImages(request.toDto()).map { result ->
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
