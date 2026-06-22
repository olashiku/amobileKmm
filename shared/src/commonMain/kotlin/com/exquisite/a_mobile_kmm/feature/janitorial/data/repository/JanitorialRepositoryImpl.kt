package com.exquisite.a_mobile_kmm.feature.janitorial.data.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.safeApiCall
import com.exquisite.a_mobile_kmm.feature.janitorial.data.remote.request.CreateJanitorialRequestDto
import com.exquisite.a_mobile_kmm.feature.janitorial.data.remote.response.CreateJanitorialResponseDto
import com.exquisite.a_mobile_kmm.feature.janitorial.domain.repository.JanitorialRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow

class JanitorialRepositoryImpl(private val httpClient: HttpClient) : JanitorialRepository {

    override suspend fun createJanitorial(request: CreateJanitorialRequestDto): Flow<Result<CreateJanitorialResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/janitorial/create") {
                setBody(request)
            }
        }
    }
}
