package com.exquisite.a_mobile_kmm.feature.septic.domain.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.feature.septic.data.remote.request.SendEnquiryRequestDto
import com.exquisite.a_mobile_kmm.feature.septic.data.remote.response.*
import com.exquisite.a_mobile_kmm.feature.septic.domain.model.CompleteSepticPaymentRequest
import com.exquisite.a_mobile_kmm.feature.septic.domain.model.DebitFromAccountSepticRequest
import com.exquisite.a_mobile_kmm.feature.septic.domain.model.InitSepticPaymentRequest
import com.exquisite.a_mobile_kmm.feature.septic.domain.model.SendEnquiryModel
import kotlinx.coroutines.flow.Flow

interface SepticRepository {
    suspend fun getSepticTruckSize(): Flow<Result<GetSepticTruckSizeResponseDto>>
    suspend fun initPayment(request: InitSepticPaymentRequest): Flow<Result<InitSepticPaymentResponseDto>>
    suspend fun debitFromAccount(request: DebitFromAccountSepticRequest): Flow<Result<DebitFromAccountSepticResponseDto>>
    suspend fun sendEnquiry(request: SendEnquiryRequestDto): Flow<Result<SendEnquiryResponseDto>>
    suspend fun completePayment(request: CompleteSepticPaymentRequest): Flow<Result<CompleteSepticPaymentResponseDto>>
}
