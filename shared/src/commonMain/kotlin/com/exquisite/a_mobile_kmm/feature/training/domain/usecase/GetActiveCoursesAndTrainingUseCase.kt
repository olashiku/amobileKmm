package com.exquisite.a_mobile_kmm.feature.training.domain.usecase

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.handleException
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.training.data.mapper.toTrainingCoursesModel
import com.exquisite.a_mobile_kmm.feature.training.domain.model.TrainingCoursesModel
import com.exquisite.a_mobile_kmm.feature.training.domain.repository.TrainingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GetActiveCoursesAndTrainingUseCase(private val trainingRepository: TrainingRepository) {

    suspend operator fun invoke(): Flow<UseCaseResult<TrainingCoursesModel>> {
        return trainingRepository.getActiveCoursesAndTraining().map { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data.responseCode == "00") {
                        val trainingCoursesModel = result.data.toTrainingCoursesModel()
                        if (trainingCoursesModel != null) {
                            UseCaseResult.Success(trainingCoursesModel)
                        } else {
                            UseCaseResult.Error("Invalid training courses response data")
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
