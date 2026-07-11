package com.exquisite.a_mobile_kmm.feature.cleaners_registration.domain.usecase

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.handleException
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.data.mapper.toRegisterCleanerModel
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.data.mapper.toRequestDto
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.domain.model.RegisterCleanerModel
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.domain.model.RegisterCleanerRequest
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.domain.repository.CleanersRegistrationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class RegisterCleanerUseCase(private val repository: CleanersRegistrationRepository) {

    suspend operator fun invoke(request: RegisterCleanerRequest): Flow<UseCaseResult<RegisterCleanerModel>> {
        return repository.registerCleaner(request.toRequestDto()).map { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data.responseCode == "00") {
                        UseCaseResult.Success(result.data.toRegisterCleanerModel())
                    } else {
                        UseCaseResult.Error(result.data.responseMessage)
                    }
                }
                is Result.Exception -> {
                    UseCaseResult.Error(result.exception.handleException())
                }
            }
        }.catch { exception ->
            emit(UseCaseResult.Error(exception.handleException()))
        }.flowOn(Dispatchers.IO)
    }
}
