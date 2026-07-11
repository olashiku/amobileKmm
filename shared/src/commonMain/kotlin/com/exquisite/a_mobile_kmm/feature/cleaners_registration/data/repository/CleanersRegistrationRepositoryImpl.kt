package com.exquisite.a_mobile_kmm.feature.cleaners_registration.data.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.safeApiCall
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.data.mapper.toRequestDto
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.data.remote.request.RegisterCleanerRequestDto
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.data.remote.response.RegisterCleanerResponseDto
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.domain.model.RegisterCleanerRequest
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.domain.repository.CleanersRegistrationRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow

class CleanersRegistrationRepositoryImpl(private val httpClient: HttpClient) : CleanersRegistrationRepository {

    override suspend fun registerCleaner(request: RegisterCleanerRequestDto): Flow<Result<RegisterCleanerResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/service_delivery/register_cleaner") {
                setBody(request)
            }
        }
    }
}
