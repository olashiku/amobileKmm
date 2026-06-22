package com.exquisite.a_mobile_kmm.feature.wallet.domain.usecase

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.handleException
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.wallet.data.mapper.toBalanceModel
import com.exquisite.a_mobile_kmm.feature.wallet.domain.model.BalanceModel
import com.exquisite.a_mobile_kmm.feature.wallet.domain.repository.WalletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GetCustomerBalanceUseCase(private val repository: WalletRepository) {
    suspend operator fun invoke(customerId: Int): Flow<UseCaseResult<BalanceModel>> {
        return repository.getCustomerBalance(customerId).map { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data.responseCode == "00" && result.data.data != null) {
                        val balance = result.data.toBalanceModel()
                        if (balance != null) {
                            UseCaseResult.Success(balance)
                        } else {
                            UseCaseResult.Error("No balance data found")
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
