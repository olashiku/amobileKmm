package com.exquisite.a_mobile_kmm.feature.pest_control.domain.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.feature.pest_control.data.remote.request.*
import com.exquisite.a_mobile_kmm.feature.pest_control.data.remote.response.*
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.GetPestControlPriceModel
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.RequestCommercialPestControlModel
import kotlinx.coroutines.flow.Flow

interface PestControlRepository {
    suspend fun requestCommercialPestControl(request: RequestCommercialPestControlRequestDto): Flow<Result<RequestCommercialPestControlResponseDto>>
    suspend fun getServiceList(): Flow<Result<GetServiceListResponseDto>>
    suspend fun getPestControlPrice(request: GetPestControlPriceRequestDto): Flow<Result<GetPestControlPriceResponseDto>>
    suspend fun debitFromWallet(request: DebitFromWalletPestControlRequestDto): Flow<Result<DebitFromWalletPestControlResponseDto>>
    suspend fun initPayment(request: InitPestControlPaymentRequestDto): Flow<Result<InitPestControlPaymentResponseDto>>
    suspend fun completePayment(request: CompletePestControlPaymentRequestDto): Flow<Result<CompletePestControlPaymentResponseDto>>
}
