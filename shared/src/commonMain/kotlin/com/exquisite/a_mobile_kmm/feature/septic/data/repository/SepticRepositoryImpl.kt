package com.exquisite.a_mobile_kmm.feature.septic.data.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.safeApiCall
import com.exquisite.a_mobile_kmm.feature.septic.data.remote.request.*
import com.exquisite.a_mobile_kmm.feature.septic.data.remote.response.*
import com.exquisite.a_mobile_kmm.feature.septic.domain.repository.SepticRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow

class SepticRepositoryImpl(private val httpClient: HttpClient) : SepticRepository {

    override suspend fun getSepticTruckSize(): Flow<Result<GetSepticTruckSizeResponseDto>> {
        return safeApiCall {
            httpClient.get("api/v1/septic/get_septic_truck_size")
        }
    }

    override suspend fun initPayment(request: InitSepticPaymentRequestDto): Flow<Result<InitSepticPaymentResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/septic/init_payment") {
                setBody(request)
            }
        }
    }

    override suspend fun debitFromAccount(request: DebitFromAccountSepticRequestDto): Flow<Result<DebitFromAccountSepticResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/septic/debit_from_account") {
                setBody(request)
            }
        }
    }

    override suspend fun sendEnquiry(request: SendEnquiryRequestDto): Flow<Result<SendEnquiryResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/septic/send_enquiry") {
                setBody(request)
            }
        }
    }

    override suspend fun completePayment(request: CompleteSepticPaymentRequestDto): Flow<Result<CompleteSepticPaymentResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/septic/complete_payment") {
                setBody(request)
            }
        }
    }
}
