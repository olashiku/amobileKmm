package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.safeApiCall
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.request.*
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.response.*
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.repository.CleaningServiceRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow

class CleaningServiceRepositoryImpl(private val httpClient: HttpClient) : CleaningServiceRepository {

    override suspend fun findAllRegions(): Flow<Result<FindAllRegionsResponseDto>> {
        return safeApiCall {
            httpClient.get("api/v1/service_delivery/find_all_regions")
        }
    }

    override suspend fun findLocationByRegion(regionId: Int): Flow<Result<FindLocationByRegionResponseDto>> {
        return safeApiCall {
            httpClient.get("api/v1/service_delivery/find_location_by_region/$regionId")
        }
    }

    override suspend fun findApartmentType(): Flow<Result<FindApartmentTypeResponseDto>> {
        return safeApiCall {
            httpClient.get("api/v1/service_delivery/find_apartment_type")
        }
    }

    override suspend fun findCleaningType(): Flow<Result<FindCleaningTypeResponseDto>> {
        return safeApiCall {
            httpClient.get("api/v1/service_delivery/find_cleaning_type")
        }
    }

    override suspend fun findNumberOfRooms(): Flow<Result<FindNumberOfRoomsResponseDto>> {
        return safeApiCall {
            httpClient.get("api/v1/service_delivery/find_number_of_rooms")
        }
    }

    override suspend fun getCleaningPrice(request: GetCleaningPriceRequestDto): Flow<Result<GetCleaningPriceResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/service_delivery/get_cleaning_price") {
                setBody(request)
            }
        }
    }

    override suspend fun debitFromWalletDeepCleaningPayment(request: DebitFromWalletDeepCleaningPaymentRequestDto): Flow<Result<DebitFromWalletDeepCleaningPaymentResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/service_delivery/debit_from_wallet_deep_cleaning_payment") {
                setBody(request)
            }
        }
    }

    override suspend fun initDeepCleaningPayment(request: InitDeepCleaningPaymentRequestDto): Flow<Result<InitDeepCleaningPaymentResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/service_delivery/init_deep_cleaning_payment") {
                setBody(request)
            }
        }
    }

    override suspend fun completeDeepCleaningPayment(request: CompleteDeepCleaningPaymentRequestDto): Flow<Result<CompleteDeepCleaningPaymentResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/service_delivery/complete_deep_cleaning_payment") {
                setBody(request)
            }
        }
    }

    override suspend fun getBasicCleaningLocations(): Flow<Result<GetBasicCleaningLocationsResponseDto>> {
        return safeApiCall {
            httpClient.get("api/v1/service_delivery/get_basic_cleaning_locations")
        }
    }
}
