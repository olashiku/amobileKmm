package com.exquisite.a_mobile_kmm.feature.training.data.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.safeApiCall
import com.exquisite.a_mobile_kmm.feature.training.data.remote.request.CompleteEnrollTrainingRequestDto
import com.exquisite.a_mobile_kmm.feature.training.data.remote.request.EnrollTrainingByAccountBalanceRequestDto
import com.exquisite.a_mobile_kmm.feature.training.data.remote.request.InitEnrollTrainingRequestDto
import com.exquisite.a_mobile_kmm.feature.training.data.remote.response.CompleteEnrollTrainingResponseDto
import com.exquisite.a_mobile_kmm.feature.training.data.remote.response.EnrollTrainingByAccountBalanceResponseDto
import com.exquisite.a_mobile_kmm.feature.training.data.remote.response.GetActiveCoursesAndTrainingResponseDto
import com.exquisite.a_mobile_kmm.feature.training.data.remote.response.InitEnrollTrainingResponseDto
import com.exquisite.a_mobile_kmm.feature.training.domain.repository.TrainingRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow

class TrainingRepositoryImpl(private val httpClient: HttpClient) : TrainingRepository {

    override suspend fun getActiveCoursesAndTraining(): Flow<Result<GetActiveCoursesAndTrainingResponseDto>> {
        return safeApiCall {
            httpClient.get("api/v1/service_delivery/get_active_courses_and_training")
        }
    }

    override suspend fun initEnrollTraining(request: InitEnrollTrainingRequestDto): Flow<Result<InitEnrollTrainingResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/service_delivery/init_enroll_training") {
                setBody(request)
            }
        }
    }

    override suspend fun completeEnrollTraining(request: CompleteEnrollTrainingRequestDto): Flow<Result<CompleteEnrollTrainingResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/service_delivery/complete_enroll_training") {
                setBody(request)
            }
        }
    }

    override suspend fun enrollTrainingByAccountBalance(request: EnrollTrainingByAccountBalanceRequestDto): Flow<Result<EnrollTrainingByAccountBalanceResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/service_delivery/enroll_training_by_account_balance") {
                setBody(request)
            }
        }
    }
}
