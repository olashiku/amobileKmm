package com.exquisite.a_mobile_kmm.feature.pest_control.domain.usecase

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.handleException
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.pest_control.data.mapper.toDto
import com.exquisite.a_mobile_kmm.feature.pest_control.data.mapper.toPestControlResponseModel
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.DebitFromWalletPestControlRequest
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.PestControlResponseModel
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.repository.PestControlRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class DebitFromWalletPestControlUseCase(private val repository: PestControlRepository) {
    suspend operator fun invoke(request: DebitFromWalletPestControlRequest): Flow<UseCaseResult<PestControlResponseModel>> {
        return repository.debitFromWallet(request.toDto()).map { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data.responseCode == "00") {
                        UseCaseResult.Success(result.data.toPestControlResponseModel())
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
