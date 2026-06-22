package com.exquisite.a_mobile_kmm.feature.auth.domain.usecase

import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.handleException
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.auth.data.mapper.toLoginModel
import com.exquisite.a_mobile_kmm.feature.auth.data.remote.request.LoginRequestDto
import com.exquisite.a_mobile_kmm.feature.auth.domain.model.LoginModel
import com.exquisite.a_mobile_kmm.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class LoginUseCase(private val authRepository: AuthRepository,
                   private val dataStore: AMobileDataStore) {

    suspend operator fun invoke(
        email: String,
        password: String
    ): Flow<UseCaseResult<LoginModel>> {
        val request = LoginRequestDto(
            email = email,
            password = password
        )

        return authRepository.login(request).map { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data.responseCode == "00") {
                        val loginModel = result.data.toLoginModel()
                        if (loginModel != null) {
                            UseCaseResult.Success(loginModel)
                        } else {
                            UseCaseResult.Error("Invalid login response data")
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

    suspend fun saveRememberMe(rememberMe: Boolean) {
        dataStore.saveRememberMe(rememberMe)
    }

    fun getRememberMe(): Flow<Boolean> {
        return dataStore.getRememberMe()
    }
}
