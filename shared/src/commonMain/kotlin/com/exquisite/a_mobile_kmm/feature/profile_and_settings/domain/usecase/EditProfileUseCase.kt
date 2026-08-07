package com.exquisite.a_mobile_kmm.feature.profile_and_settings.domain.usecase

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.handleException
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.profile_and_settings.data.mapper.toProfileResponseModel
import com.exquisite.a_mobile_kmm.feature.profile_and_settings.domain.model.EditProfileRequest
import com.exquisite.a_mobile_kmm.feature.profile_and_settings.domain.model.ProfileResponseModel
import com.exquisite.a_mobile_kmm.feature.profile_and_settings.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class EditProfileUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(request: EditProfileRequest): Flow<UseCaseResult<ProfileResponseModel>> {
        return repository.editProfile(request).map { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data.responseCode == "00") {
                        UseCaseResult.Success(result.data.toProfileResponseModel())
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
