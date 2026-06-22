package com.exquisite.a_mobile_kmm.feature.septic.domain.usecase

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.handleException
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.septic.data.mapper.toSepticPaymentModel
import com.exquisite.a_mobile_kmm.feature.septic.data.remote.request.InitSepticPaymentRequestDto
import com.exquisite.a_mobile_kmm.feature.septic.domain.model.SepticPaymentModel
import com.exquisite.a_mobile_kmm.feature.septic.domain.repository.SepticRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class InitSepticPaymentUseCase(private val repository: SepticRepository) {
    suspend operator fun invoke(request: InitSepticPaymentRequestDto): Flow<UseCaseResult<SepticPaymentModel>> {
        return repository.initPayment(request).map { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data.responseCode == "00" && result.data.data != null) {
                        val paymentModel = result.data.toSepticPaymentModel()
                        if (paymentModel != null) {
                            UseCaseResult.Success(paymentModel)
                        } else {
                            UseCaseResult.Error("Invalid payment data")
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
