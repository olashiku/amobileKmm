package com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.usecase

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.handleException
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.mapper.toToiletPaymentModel
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.remote.request.InitToiletPaymentRequestDto
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.ToiletPaymentModel
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.repository.MobileToiletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class InitToiletPaymentUseCase(private val repository: MobileToiletRepository) {
    suspend operator fun invoke(request: InitToiletPaymentRequestDto): Flow<UseCaseResult<ToiletPaymentModel>> {
        return repository.initToiletPayment(request).map { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data.responseCode == "00" && result.data.data != null) {
                        val model = result.data.toToiletPaymentModel()
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
