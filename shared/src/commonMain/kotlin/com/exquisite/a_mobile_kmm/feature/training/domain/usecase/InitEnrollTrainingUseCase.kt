package com.exquisite.a_mobile_kmm.feature.training.domain.usecase

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.handleException
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.training.data.mapper.toInitEnrollTrainingModel
import com.exquisite.a_mobile_kmm.feature.training.data.remote.request.InitEnrollTrainingRequestDto
import com.exquisite.a_mobile_kmm.feature.training.domain.model.InitEnrollTrainingModel
import com.exquisite.a_mobile_kmm.feature.training.domain.repository.TrainingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class InitEnrollTrainingUseCase(private val trainingRepository: TrainingRepository) {

    suspend operator fun invoke(request: InitEnrollTrainingRequestDto): Flow<UseCaseResult<InitEnrollTrainingModel>> {
        return trainingRepository.initEnrollTraining(request).map { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data.responseCode == "00") {
                        val initEnrollModel = result.data.toInitEnrollTrainingModel()
                        if (initEnrollModel != null) {
                            UseCaseResult.Success(initEnrollModel)
                        } else {
                            UseCaseResult.Error("Invalid enrollment response data")
                        }
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
