package com.exquisite.a_mobile_kmm.feature.pest_control.data.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.safeApiCall
import com.exquisite.a_mobile_kmm.feature.pest_control.data.remote.request.*
import com.exquisite.a_mobile_kmm.feature.pest_control.data.remote.response.*
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.repository.PestControlRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow

class PestControlRepositoryImpl(private val httpClient: HttpClient) : PestControlRepository {

    override suspend fun requestCommercialPestControl(request: RequestCommercialPestControlRequestDto): Flow<Result<RequestCommercialPestControlResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/pest_control/request_commercial_pest_control") {
                setBody(request)
            }
        }
    }

    override suspend fun getServiceList(): Flow<Result<GetServiceListResponseDto>> {
        return safeApiCall {
            httpClient.get("api/v1/pest_control/get_service_list")
        }
    }

    override suspend fun getPestControlPrice(request: GetPestControlPriceRequestDto): Flow<Result<GetPestControlPriceResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/pest_control/get_price") {
                setBody(request)
            }
        }
    }

    override suspend fun debitFromWallet(request: DebitFromWalletPestControlRequestDto): Flow<Result<DebitFromWalletPestControlResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/pest_control/debit_from_wallet") {
                setBody(request)
            }
        }
    }

    override suspend fun initPayment(request: InitPestControlPaymentRequestDto): Flow<Result<InitPestControlPaymentResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/pest_control/init_payment") {
                setBody(request)
            }
        }
    }

    override suspend fun completePayment(request: CompletePestControlPaymentRequestDto): Flow<Result<CompletePestControlPaymentResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/pest_control/complete_payment") {
                setBody(request)
            }
        }
    }
}
