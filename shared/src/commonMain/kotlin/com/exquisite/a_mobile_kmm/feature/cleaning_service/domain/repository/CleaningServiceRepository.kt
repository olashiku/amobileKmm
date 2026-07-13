package com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.request.*
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.response.*
import kotlinx.coroutines.flow.Flow

interface CleaningServiceRepository {
    suspend fun findAllRegions(): Flow<Result<FindAllRegionsResponseDto>>
    suspend fun findLocationByRegion(regionId: Int): Flow<Result<FindLocationByRegionResponseDto>>
    suspend fun findApartmentType(): Flow<Result<FindApartmentTypeResponseDto>>
    suspend fun findCleaningType(): Flow<Result<FindCleaningTypeResponseDto>>
    suspend fun findNumberOfRooms(): Flow<Result<FindNumberOfRoomsResponseDto>>
    suspend fun getCleaningPrice(request: GetCleaningPriceRequestDto): Flow<Result<GetCleaningPriceResponseDto>>
    suspend fun debitFromWalletDeepCleaningPayment(request: DebitFromWalletDeepCleaningPaymentRequestDto): Flow<Result<DebitFromWalletDeepCleaningPaymentResponseDto>>
    suspend fun initDeepCleaningPayment(request: InitDeepCleaningPaymentRequestDto): Flow<Result<InitDeepCleaningPaymentResponseDto>>
    suspend fun completeDeepCleaningPayment(request: CompleteDeepCleaningPaymentRequestDto): Flow<Result<CompleteDeepCleaningPaymentResponseDto>>
    suspend fun getBasicCleaningLocations(): Flow<Result<GetBasicCleaningLocationsResponseDto>>
    suspend fun checkBasicCleaningEligibility(customerId: Int): Flow<Result<CheckBasicCleaningEligibilityResponseDto>>
    suspend fun getBasicCleaningBreakdown(request: GetBasicCleaningBreakdownRequestDto): Flow<Result<GetBasicCleaningBreakdownResponseDto>>
    suspend fun initBasicCleaningPayment(request: InitBasicCleaningPaymentRequestDto): Flow<Result<InitBasicCleaningPaymentResponseDto>>
    suspend fun debitFromWalletBasicCleaningPayment(request: DebitFromWalletBasicCleaningPaymentRequestDto): Flow<Result<DebitFromWalletBasicCleaningPaymentResponseDto>>
    suspend fun completeBasicCleaningPayment(request: CompleteBasicCleaningPaymentRequestDto): Flow<Result<CompleteBasicCleaningPaymentResponseDto>>
}
