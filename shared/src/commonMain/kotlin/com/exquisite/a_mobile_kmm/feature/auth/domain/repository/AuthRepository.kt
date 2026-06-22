package com.exquisite.a_mobile_kmm.feature.auth.domain.repository

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
import kotlinx.coroutines.flow.Flow
import  com.exquisite.a_mobile_kmm.core.network.Result


interface  AuthRepository {
    suspend fun initRegister(request: InitRegisterRequestDto): Flow<Result<InitRegisterResponseDto>>
    suspend fun completeRegister(request: CompleteRegisterRequestDto): Flow<Result<CompleteRegisterResponseDto>>
    suspend fun initForgotPassword(request: InitForgotPasswordRequestDto): Flow<Result<InitForgotPasswordResponseDto>>
    suspend fun completeForgotPassword(request: CompleteForgotPasswordRequestDto): Flow<Result<CompleteForgotPasswordResponseDto>>
    suspend fun login(request: LoginRequestDto): Flow<Result<LoginResponseDto>>
    suspend fun resendOtp(request: ResendOtpRequestDto): Flow<Result<ResendOtpResponseDto>>
    suspend fun validateOtp(request: ValidateOtpRequestDto): Flow<Result<ValidateOtpResponseDto>>
    suspend fun uploadFile(fileBytes: ByteArray, fileName: String): Flow<Result<UploadResponseDto>>
}