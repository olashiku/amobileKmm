package com.exquisite.a_mobile_kmm.feature.profile_and_settings.domain.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.feature.profile_and_settings.data.remote.response.*
import com.exquisite.a_mobile_kmm.feature.profile_and_settings.domain.model.ChangePasswordRequest
import com.exquisite.a_mobile_kmm.feature.profile_and_settings.domain.model.EditProfileRequest
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun editProfile(request: EditProfileRequest): Flow<Result<EditProfileResponseDto>>
    suspend fun changePassword(request: ChangePasswordRequest): Flow<Result<ChangePasswordResponseDto>>
}
