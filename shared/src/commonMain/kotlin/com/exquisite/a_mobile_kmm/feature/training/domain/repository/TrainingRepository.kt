package com.exquisite.a_mobile_kmm.feature.training.domain.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.feature.training.data.remote.request.CompleteEnrollTrainingRequestDto
import com.exquisite.a_mobile_kmm.feature.training.data.remote.request.EnrollTrainingByAccountBalanceRequestDto
import com.exquisite.a_mobile_kmm.feature.training.data.remote.request.InitEnrollTrainingRequestDto
import com.exquisite.a_mobile_kmm.feature.training.data.remote.response.CompleteEnrollTrainingResponseDto
import com.exquisite.a_mobile_kmm.feature.training.data.remote.response.EnrollTrainingByAccountBalanceResponseDto
import com.exquisite.a_mobile_kmm.feature.training.data.remote.response.GetActiveCoursesAndTrainingResponseDto
import com.exquisite.a_mobile_kmm.feature.training.data.remote.response.InitEnrollTrainingResponseDto
import kotlinx.coroutines.flow.Flow

interface TrainingRepository {
    suspend fun getActiveCoursesAndTraining(): Flow<Result<GetActiveCoursesAndTrainingResponseDto>>
    suspend fun initEnrollTraining(request: InitEnrollTrainingRequestDto): Flow<Result<InitEnrollTrainingResponseDto>>
    suspend fun completeEnrollTraining(request: CompleteEnrollTrainingRequestDto): Flow<Result<CompleteEnrollTrainingResponseDto>>
    suspend fun enrollTrainingByAccountBalance(request: EnrollTrainingByAccountBalanceRequestDto): Flow<Result<EnrollTrainingByAccountBalanceResponseDto>>
}
