package com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.remote.request.*
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.remote.response.*
import kotlinx.coroutines.flow.Flow

interface MobileToiletRepository {
    suspend fun requestForConstruction(request: RequestForConstructionRequestDto): Flow<Result<RequestForConstructionResponseDto>>
    suspend fun initToiletPayment(request: InitToiletPaymentRequestDto): Flow<Result<InitToiletPaymentResponseDto>>
    suspend fun getToiletPrice(request: GetToiletPriceRequestDto): Flow<Result<GetToiletPriceResponseDto>>
    suspend fun completeToiletPayment(request: CompleteToiletPaymentRequestDto): Flow<Result<CompleteToiletPaymentResponseDto>>
    suspend fun debitFromAccount(request: DebitFromAccountRequestDto): Flow<Result<DebitFromAccountResponseDto>>
    suspend fun getStandardToiletsList(): Flow<Result<GetStandardToiletsListResponseDto>>
    suspend fun getEventType(): Flow<Result<GetEventTypeResponseDto>>
    suspend fun checkToiletAvailability(request: CheckToiletAvailabilityRequestDto): Flow<Result<CheckToiletAvailabilityResponseDto>>
}
