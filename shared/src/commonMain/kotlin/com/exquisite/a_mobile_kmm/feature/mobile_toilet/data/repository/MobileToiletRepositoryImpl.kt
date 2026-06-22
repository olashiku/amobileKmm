package com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.safeApiCall
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.remote.request.*
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.remote.response.*
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.repository.MobileToiletRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow

class MobileToiletRepositoryImpl(private val httpClient: HttpClient) : MobileToiletRepository {

    override suspend fun requestForConstruction(request: RequestForConstructionRequestDto): Flow<Result<RequestForConstructionResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/toilets/request_for_construction") {
                setBody(request)
            }
        }
    }

    override suspend fun initToiletPayment(request: InitToiletPaymentRequestDto): Flow<Result<InitToiletPaymentResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/toilets/init_toilet_payment") {
                setBody(request)
            }
        }
    }

    override suspend fun getToiletPrice(request: GetToiletPriceRequestDto): Flow<Result<GetToiletPriceResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/toilets/get_toilet_price") {
                setBody(request)
            }
        }
    }

    override suspend fun completeToiletPayment(request: CompleteToiletPaymentRequestDto): Flow<Result<CompleteToiletPaymentResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/toilets/complete_payment") {
                setBody(request)
            }
        }
    }

    override suspend fun debitFromAccount(request: DebitFromAccountRequestDto): Flow<Result<DebitFromAccountResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/toilets/debit_from_account") {
                setBody(request)
            }
        }
    }

    override suspend fun getStandardToiletsList(): Flow<Result<GetStandardToiletsListResponseDto>> {
        return safeApiCall {
            httpClient.get("api/v1/toilets/get_standard_toilets_list")
        }
    }

    override suspend fun getEventType(): Flow<Result<GetEventTypeResponseDto>> {
        return safeApiCall {
            httpClient.get("api/v1/toilets/get_event_type")
        }
    }

    override suspend fun checkToiletAvailability(request: CheckToiletAvailabilityRequestDto): Flow<Result<CheckToiletAvailabilityResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/toilets/check_toilet_availability") {
                setBody(request)
            }
        }
    }
}
