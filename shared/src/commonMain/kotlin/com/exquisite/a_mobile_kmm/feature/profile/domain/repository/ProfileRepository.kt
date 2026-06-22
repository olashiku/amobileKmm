package com.exquisite.a_mobile_kmm.feature.profile.domain.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.feature.profile.data.remote.request.*
import com.exquisite.a_mobile_kmm.feature.profile.data.remote.response.*
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun editProfile(request: EditProfileRequestDto): Flow<Result<EditProfileResponseDto>>
    suspend fun changePassword(request: ChangePasswordRequestDto): Flow<Result<ChangePasswordResponseDto>>
}
