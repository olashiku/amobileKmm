package com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.handleException
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.mapper.toDto
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.mapper.toPaymentModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.InitDeepCleaningPaymentRequest
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.PaymentModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.repository.CleaningServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class InitDeepCleaningPaymentUseCase(private val repository: CleaningServiceRepository) {
    suspend operator fun invoke(request: InitDeepCleaningPaymentRequest): Flow<UseCaseResult<PaymentModel>> {
        return repository.initDeepCleaningPayment(request.toDto()).map { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data.responseCode == "00" && result.data.data != null) {
                        val model = result.data.toPaymentModel()
                        if (model != null) {
                            UseCaseResult.Success(model)
                        } else {
                            UseCaseResult.Error("No data found")
                        }
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
