package com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.handleException
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.mapper.toCleaningPriceModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.request.GetCleaningPriceRequestDto
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.CleaningPriceModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.repository.CleaningServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GetCleaningPriceUseCase(private val repository: CleaningServiceRepository) {
    suspend operator fun invoke(request: GetCleaningPriceRequestDto): Flow<UseCaseResult<CleaningPriceModel>> {
        return repository.getCleaningPrice(request).map { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data.responseCode == "00" && result.data.data != null) {
                        val model = result.data.toCleaningPriceModel()
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
