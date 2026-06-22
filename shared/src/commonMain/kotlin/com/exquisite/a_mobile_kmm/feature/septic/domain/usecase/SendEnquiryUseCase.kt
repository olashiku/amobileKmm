package com.exquisite.a_mobile_kmm.feature.septic.domain.usecase

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.handleException
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.septic.data.mapper.toSepticResponseModel
import com.exquisite.a_mobile_kmm.feature.septic.data.remote.request.SendEnquiryRequestDto
import com.exquisite.a_mobile_kmm.feature.septic.domain.model.SepticResponseModel
import com.exquisite.a_mobile_kmm.feature.septic.domain.repository.SepticRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class SendEnquiryUseCase(private val repository: SepticRepository) {
    suspend operator fun invoke(request: SendEnquiryRequestDto): Flow<UseCaseResult<SepticResponseModel>> {
        return repository.sendEnquiry(request).map { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data.responseCode == "00") {
                        UseCaseResult.Success(result.data.toSepticResponseModel())
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
