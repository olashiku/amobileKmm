package com.exquisite.a_mobile_kmm.feature.septic.data.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.safeApiCall
import com.exquisite.a_mobile_kmm.feature.septic.data.mapper.toRequestDto
import com.exquisite.a_mobile_kmm.feature.septic.data.remote.request.SendEnquiryRequestDto
import com.exquisite.a_mobile_kmm.feature.septic.data.remote.response.*
import com.exquisite.a_mobile_kmm.feature.septic.domain.model.CompleteSepticPaymentRequest
import com.exquisite.a_mobile_kmm.feature.septic.domain.model.DebitFromAccountSepticRequest
import com.exquisite.a_mobile_kmm.feature.septic.domain.model.InitSepticPaymentRequest
import com.exquisite.a_mobile_kmm.feature.septic.domain.model.SendEnquiryModel
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

    override suspend fun initPayment(request: InitSepticPaymentRequest): Flow<Result<InitSepticPaymentResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/septic/init_payment") {
                setBody(request.toRequestDto())
            }
        }
    }

    override suspend fun debitFromAccount(request: DebitFromAccountSepticRequest): Flow<Result<DebitFromAccountSepticResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/septic/debit_from_account") {
                setBody(request.toRequestDto())
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

    override suspend fun completePayment(request: CompleteSepticPaymentRequest): Flow<Result<CompleteSepticPaymentResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/septic/complete_payment") {
                setBody(request.toRequestDto())
            }
        }
    }
}
