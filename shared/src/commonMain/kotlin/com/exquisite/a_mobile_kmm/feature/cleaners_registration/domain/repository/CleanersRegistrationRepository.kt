package com.exquisite.a_mobile_kmm.feature.cleaners_registration.domain.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.data.remote.request.RegisterCleanerRequestDto
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.data.remote.response.RegisterCleanerResponseDto
import kotlinx.coroutines.flow.Flow

interface CleanersRegistrationRepository {
    suspend fun registerCleaner(request: RegisterCleanerRequestDto): Flow<Result<RegisterCleanerResponseDto>>
}
