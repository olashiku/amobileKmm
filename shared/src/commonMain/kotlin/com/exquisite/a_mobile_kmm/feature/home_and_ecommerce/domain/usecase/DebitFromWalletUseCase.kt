package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.usecase

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.handleException
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.mapper.toDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.mapper.toPaymentSuccessModel
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.DebitFromWalletRequest
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.PaymentSuccessModel
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.repository.EcommerceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class DebitFromWalletUseCase(private val ecommerceRepository: EcommerceRepository) {

    suspend operator fun invoke(request: DebitFromWalletRequest): Flow<UseCaseResult<PaymentSuccessModel>> {
        return ecommerceRepository.debitFromWallet(request.toDto()).map { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data.responseCode == "00") {
                        UseCaseResult.Success(result.data.toPaymentSuccessModel())
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
