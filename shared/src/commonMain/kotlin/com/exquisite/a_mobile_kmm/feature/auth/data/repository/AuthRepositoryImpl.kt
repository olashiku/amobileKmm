package com.exquisite.a_mobile_kmm.feature.auth.data.repository

import com.exquisite.a_mobile_kmm.core.network.safeApiCall
import com.exquisite.a_mobile_kmm.feature.auth.data.remote.request.CompleteForgotPasswordRequestDto
import com.exquisite.a_mobile_kmm.feature.auth.data.remote.request.CompleteRegisterRequestDto
import com.exquisite.a_mobile_kmm.feature.auth.data.remote.request.InitForgotPasswordRequestDto
import com.exquisite.a_mobile_kmm.feature.auth.data.remote.request.InitRegisterRequestDto
import com.exquisite.a_mobile_kmm.feature.auth.data.remote.request.LoginRequestDto
import com.exquisite.a_mobile_kmm.feature.auth.data.remote.request.ResendOtpRequestDto
import com.exquisite.a_mobile_kmm.feature.auth.data.remote.request.ValidateOtpRequestDto
import com.exquisite.a_mobile_kmm.feature.auth.data.remote.response.CompleteForgotPasswordResponseDto
import com.exquisite.a_mobile_kmm.feature.auth.data.remote.response.CompleteRegisterResponseDto
import com.exquisite.a_mobile_kmm.feature.auth.data.remote.response.InitForgotPasswordResponseDto
import com.exquisite.a_mobile_kmm.feature.auth.data.remote.response.InitRegisterResponseDto
import com.exquisite.a_mobile_kmm.feature.auth.data.remote.response.LoginResponseDto
import com.exquisite.a_mobile_kmm.feature.auth.data.remote.response.ResendOtpResponseDto
import com.exquisite.a_mobile_kmm.feature.auth.data.remote.response.UploadResponseDto
import com.exquisite.a_mobile_kmm.feature.auth.data.remote.response.ValidateOtpResponseDto
import com.exquisite.a_mobile_kmm.feature.auth.domain.repository.AuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.Flow
import  com.exquisite.a_mobile_kmm.core.network.Result

class AuthRepositoryImpl(    private val httpClient: HttpClient): AuthRepository {

    override suspend fun initRegister(request: InitRegisterRequestDto): Flow<Result<InitRegisterResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/auth/init_register") {
                setBody(request)
            }
        }
    }

    override suspend fun completeRegister(request: CompleteRegisterRequestDto): Flow<Result<CompleteRegisterResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/auth/complete_register") {
                setBody(request)
            }
        }
    }

    override suspend fun initForgotPassword(request: InitForgotPasswordRequestDto): Flow<Result<InitForgotPasswordResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/auth/init_forgot_password") {
                setBody(request)
            }
        }
    }

    override suspend fun completeForgotPassword(request: CompleteForgotPasswordRequestDto): Flow<Result<CompleteForgotPasswordResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/auth/complete_forgot_password") {
                setBody(request)
            }
        }
    }

    override suspend fun login(request: LoginRequestDto): Flow<Result<LoginResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/auth/login") {
                setBody(request)
            }
        }
    }

    override suspend fun resendOtp(request: ResendOtpRequestDto): Flow<Result<ResendOtpResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/auth/resend_otp") {
                setBody(request)
            }
        }
    }

    override suspend fun validateOtp(request: ValidateOtpRequestDto): Flow<Result<ValidateOtpResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/auth/validate_otp") {
                setBody(request)
            }
        }
    }

    override suspend fun uploadFile(fileBytes: ByteArray, fileName: String): Flow<Result<UploadResponseDto>> {
        return safeApiCall {
            httpClient.submitFormWithBinaryData(
                url = "api/upload",
                formData = formData {
                    append(
                        "file",
                        fileBytes,
                        Headers.build {
                            append(HttpHeaders.ContentDisposition, "filename=\"$fileName\"")
                        }
                    )
                }
            )
        }
    }
}