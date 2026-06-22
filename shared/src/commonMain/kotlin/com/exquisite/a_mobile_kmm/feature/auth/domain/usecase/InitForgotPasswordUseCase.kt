package com.exquisite.a_mobile_kmm.feature.auth.domain.usecase

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.handleException
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.auth.data.remote.request.InitForgotPasswordRequestDto
import com.exquisite.a_mobile_kmm.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class InitForgotPasswordUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(email: String): Flow<UseCaseResult<String>> {
        val request = InitForgotPasswordRequestDto(email = email)

        return authRepository.initForgotPassword(request).map { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data.responseCode == "00" && result.data.data?.uniqueRef != null) {
                        UseCaseResult.Success(result.data.data.uniqueRef?:"")
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
