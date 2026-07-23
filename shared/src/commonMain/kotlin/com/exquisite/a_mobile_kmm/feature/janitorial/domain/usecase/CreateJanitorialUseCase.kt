package com.exquisite.a_mobile_kmm.feature.janitorial.domain.usecase

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.handleException
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.janitorial.data.mapper.toCreateJanitorialRequestDto
import com.exquisite.a_mobile_kmm.feature.janitorial.data.mapper.toJanitorialResponseModel
import com.exquisite.a_mobile_kmm.feature.janitorial.domain.model.CreateJanitorialRequestModel
import com.exquisite.a_mobile_kmm.feature.janitorial.domain.model.JanitorialResponseModel
import com.exquisite.a_mobile_kmm.feature.janitorial.domain.repository.JanitorialRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class CreateJanitorialUseCase(private val repository: JanitorialRepository) {
    suspend operator fun invoke(request: CreateJanitorialRequestModel): Flow<UseCaseResult<JanitorialResponseModel>> {
        return repository.createJanitorial(request.toCreateJanitorialRequestDto()).map { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data.responseCode == "00") {
                        UseCaseResult.Success(result.data.toJanitorialResponseModel())
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
