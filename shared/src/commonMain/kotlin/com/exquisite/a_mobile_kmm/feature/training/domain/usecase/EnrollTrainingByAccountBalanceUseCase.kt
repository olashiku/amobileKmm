package com.exquisite.a_mobile_kmm.feature.training.domain.usecase

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.handleException
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.training.data.mapper.toEnrollmentSuccessModel
import com.exquisite.a_mobile_kmm.feature.training.data.remote.request.EnrollTrainingByAccountBalanceRequestDto
import com.exquisite.a_mobile_kmm.feature.training.domain.model.EnrollmentSuccessModel
import com.exquisite.a_mobile_kmm.feature.training.domain.repository.TrainingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class EnrollTrainingByAccountBalanceUseCase(private val trainingRepository: TrainingRepository) {

    suspend operator fun invoke(request: EnrollTrainingByAccountBalanceRequestDto): Flow<UseCaseResult<EnrollmentSuccessModel>> {
        return trainingRepository.enrollTrainingByAccountBalance(request).map { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data.responseCode == "00") {
                        UseCaseResult.Success(result.data.toEnrollmentSuccessModel())
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
