package com.exquisite.a_mobile_kmm.feature.janitorial.domain.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.feature.janitorial.data.remote.request.CreateJanitorialRequestDto
import com.exquisite.a_mobile_kmm.feature.janitorial.data.remote.response.CreateJanitorialResponseDto
import kotlinx.coroutines.flow.Flow

interface JanitorialRepository {
    suspend fun createJanitorial(request: CreateJanitorialRequestDto): Flow<Result<CreateJanitorialResponseDto>>
}
