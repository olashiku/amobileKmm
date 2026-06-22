package com.exquisite.a_mobile_kmm.feature.profile.data.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.safeApiCall
import com.exquisite.a_mobile_kmm.feature.profile.data.remote.request.*
import com.exquisite.a_mobile_kmm.feature.profile.data.remote.response.*
import com.exquisite.a_mobile_kmm.feature.profile.domain.repository.ProfileRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow

class ProfileRepositoryImpl(private val httpClient: HttpClient) : ProfileRepository {

    override suspend fun editProfile(request: EditProfileRequestDto): Flow<Result<EditProfileResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/customer/edit_profile") {
                setBody(request)
            }
        }
    }

    override suspend fun changePassword(request: ChangePasswordRequestDto): Flow<Result<ChangePasswordResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/customer/change_password") {
                setBody(request)
            }
        }
    }
}
