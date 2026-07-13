package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.safeApiCall
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.data_source.CleaningServiceDataSource
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.mapper.toEntity
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.mapper.*
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.request.*
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.response.*
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.repository.CleaningServiceRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class CleaningServiceRepositoryImpl(
    private val httpClient: HttpClient,
    private val cleaningServiceDataSource: CleaningServiceDataSource
) : CleaningServiceRepository {

    override suspend fun findAllRegions(): Flow<Result<FindAllRegionsResponseDto>> {
        return flow {
            val localRegions = cleaningServiceDataSource.getAllRegions().first()

            if (localRegions.isNotEmpty()) {
                val response = FindAllRegionsResponseDto(
                    responseMessage = "Success",
                    responseCode = "00",
                    data = localRegions.map { entity ->
                        RegionDto(
                            id = entity.id,
                            name = entity.name
                        )
                    }
                )
                emit(Result.Success(response))
            }

            safeApiCall<FindAllRegionsResponseDto> {
                httpClient.get("api/v1/cleaning/find_all_regions")
            }.collect { result ->
                if (result is Result.Success) {
                    result.data.toRegionModelList()?.let { regions ->
                        cleaningServiceDataSource.saveRegions(regions.map { it.toEntity() })
                    }
                }
                emit(result)
            }
        }
    }

    override suspend fun findLocationByRegion(regionId: Int): Flow<Result<FindLocationByRegionResponseDto>> {
        return safeApiCall {
            httpClient.get("api/v1/cleaning/find_location_by_region?regionId=$regionId")
        }
    }

    override suspend fun findApartmentType(): Flow<Result<FindApartmentTypeResponseDto>> {
        return flow {
            val localApartmentTypes = cleaningServiceDataSource.getAllApartmentTypes().first()

            if (localApartmentTypes.isNotEmpty()) {
                val response = FindApartmentTypeResponseDto(
                    responseMessage = "Success",
                    responseCode = "00",
                    data = localApartmentTypes.map { entity ->
                        ApartmentTypeDto(
                            id = entity.id,
                            name = entity.name
                        )
                    }
                )
                emit(Result.Success(response))
            }

            safeApiCall<FindApartmentTypeResponseDto> {
                httpClient.get("api/v1/cleaning/find_apartment_type")
            }.collect { result ->
                if (result is Result.Success) {
                    result.data.toApartmentTypeModelList()?.let { apartmentTypes ->
                        cleaningServiceDataSource.saveApartmentTypes(apartmentTypes.map { it.toEntity() })
                    }
                }
                emit(result)
            }
        }
    }

    override suspend fun findCleaningType(): Flow<Result<FindCleaningTypeResponseDto>> {
        return flow {
            val localCleaningTypes = cleaningServiceDataSource.getAllCleaningTypes().first()

            if (localCleaningTypes.isNotEmpty()) {
                val response = FindCleaningTypeResponseDto(
                    responseMessage = "Success",
                    responseCode = "00",
                    data = localCleaningTypes.map { entity ->
                        com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.response.CleaningTypeDto(
                            id = entity.id,
                            name = entity.name
                        )
                    }
                )
                emit(Result.Success(response))
            }

            safeApiCall<FindCleaningTypeResponseDto> {
                httpClient.get("api/v1/cleaning/find_cleaning_type")
            }.collect { result ->
                if (result is Result.Success) {
                    result.data.toCleaningTypeModelList()?.let { cleaningTypes ->
                        cleaningServiceDataSource.saveCleaningTypes(cleaningTypes.map { it.toEntity() })
                    }
                }
                emit(result)
            }
        }
    }

    override suspend fun findNumberOfRooms(): Flow<Result<FindNumberOfRoomsResponseDto>> {
        return flow {
            val localNumberOfRooms = cleaningServiceDataSource.getAllNumberOfRooms().first()

            if (localNumberOfRooms.isNotEmpty()) {
                val response = FindNumberOfRoomsResponseDto(
                    responseMessage = "Success",
                    responseCode = "00",
                    data = localNumberOfRooms.map { entity ->
                        com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.response.NumberOfRoomsDto(
                            id = entity.id,
                            name = entity.name
                        )
                    }
                )
                emit(Result.Success(response))
            }

            safeApiCall<FindNumberOfRoomsResponseDto> {
                httpClient.get("api/v1/cleaning/find_number_of_rooms")
            }.collect { result ->
                if (result is Result.Success) {
                    result.data.toNumberOfRoomsModelList()?.let { numberOfRooms ->
                        cleaningServiceDataSource.saveNumberOfRooms(numberOfRooms.map { it.toEntity() })
                    }
                }
                emit(result)
            }
        }
    }

    override suspend fun getCleaningPrice(request: GetCleaningPriceRequestDto): Flow<Result<GetCleaningPriceResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/cleaning/get_cleaning_price") {
                setBody(request)
            }
        }
    }

    override suspend fun debitFromWalletDeepCleaningPayment(request: DebitFromWalletDeepCleaningPaymentRequestDto): Flow<Result<DebitFromWalletDeepCleaningPaymentResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/cleaning/debit_from_wallet_deep_cleaning_payment") {
                setBody(request)
            }
        }
    }

    override suspend fun initDeepCleaningPayment(request: InitDeepCleaningPaymentRequestDto): Flow<Result<InitDeepCleaningPaymentResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/cleaning/init_deep_cleaning_payment") {
                setBody(request)
            }
        }
    }

    override suspend fun completeDeepCleaningPayment(request: CompleteDeepCleaningPaymentRequestDto): Flow<Result<CompleteDeepCleaningPaymentResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/cleaning/complete_deep_cleaning_payment") {
                setBody(request)
            }
        }
    }

    override suspend fun getBasicCleaningLocations(): Flow<Result<GetBasicCleaningLocationsResponseDto>> {
        return safeApiCall {
            httpClient.get("api/v1/cleaning/get_basic_cleaning_locations")
        }
    }

    override suspend fun checkBasicCleaningEligibility(customerId: Int): Flow<Result<CheckBasicCleaningEligibilityResponseDto>> {
        return safeApiCall {
            httpClient.get("api/v1/cleaning/check_basic_cleaning_eligibility?customerId=$customerId")
        }
    }

    override suspend fun getBasicCleaningBreakdown(request: GetBasicCleaningBreakdownRequestDto): Flow<Result<GetBasicCleaningBreakdownResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/cleaning/get_basic_cleaning_breakdown") {
                setBody(request)
            }
        }
    }

    override suspend fun initBasicCleaningPayment(request: InitBasicCleaningPaymentRequestDto): Flow<Result<InitBasicCleaningPaymentResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/cleaning/init_basic_cleaning_payment") {
                setBody(request)
            }
        }
    }

    override suspend fun debitFromWalletBasicCleaningPayment(request: DebitFromWalletBasicCleaningPaymentRequestDto): Flow<Result<DebitFromWalletBasicCleaningPaymentResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/cleaning/debit_from_wallet_basic_cleaning_payment") {
                setBody(request)
            }
        }
    }

    override suspend fun completeBasicCleaningPayment(request: CompleteBasicCleaningPaymentRequestDto): Flow<Result<CompleteBasicCleaningPaymentResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/cleaning/complete_basic_cleaning_payment") {
                setBody(request)
            }
        }
    }
}
