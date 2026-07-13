package com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.handleException
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.mapper.toDto
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.mapper.toPaymentResponseModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.DebitFromWalletBasicCleaningPaymentRequest
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.PaymentResponseModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.repository.CleaningServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class DebitFromWalletBasicCleaningPaymentUseCase(private val repository: CleaningServiceRepository) {
    suspend operator fun invoke(request: DebitFromWalletBasicCleaningPaymentRequest): Flow<UseCaseResult<PaymentResponseModel>> {
        return repository.debitFromWalletBasicCleaningPayment(request.toDto()).map { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data.responseCode == "00") {
                        UseCaseResult.Success(result.data.toPaymentResponseModel())
                    } else {
                        UseCaseResult.Error(result.data.responseMessage)
                    }
                }
                is Result.Exception -> {
                    UseCaseResult.Error(result.exception.handleException())
                }
            }
        }.catch { exception ->
            emit(UseCaseResult.Error(exception.handleException()))
        }.flowOn(Dispatchers.IO)
    }
}
