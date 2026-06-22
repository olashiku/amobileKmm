package com.exquisite.a_mobile_kmm.feature.septic.domain.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.feature.septic.data.remote.request.*
import com.exquisite.a_mobile_kmm.feature.septic.data.remote.response.*
import kotlinx.coroutines.flow.Flow

interface SepticRepository {
    suspend fun getSepticTruckSize(): Flow<Result<GetSepticTruckSizeResponseDto>>
    suspend fun initPayment(request: InitSepticPaymentRequestDto): Flow<Result<InitSepticPaymentResponseDto>>
    suspend fun debitFromAccount(request: DebitFromAccountSepticRequestDto): Flow<Result<DebitFromAccountSepticResponseDto>>
    suspend fun sendEnquiry(request: SendEnquiryRequestDto): Flow<Result<SendEnquiryResponseDto>>
    suspend fun completePayment(request: CompleteSepticPaymentRequestDto): Flow<Result<CompleteSepticPaymentResponseDto>>
}
