package com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.usecase

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.handleException
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.mapper.toToiletResponseModel
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.remote.request.CompleteToiletPaymentRequestDto
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.ToiletResponseModel
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.repository.MobileToiletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class CompleteToiletPaymentUseCase(private val repository: MobileToiletRepository) {
    suspend operator fun invoke(request: CompleteToiletPaymentRequestDto): Flow<UseCaseResult<ToiletResponseModel>> {
        return repository.completeToiletPayment(request).map { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data.responseCode == "00") {
                        UseCaseResult.Success(result.data.toToiletResponseModel())
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
