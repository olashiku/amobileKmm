package com.exquisite.a_mobile_kmm.feature.address.domain.usecase

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.handleException
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.address.data.mapper.toAddressResponseModel
import com.exquisite.a_mobile_kmm.feature.address.data.remote.request.CreateAddressRequestDto
import com.exquisite.a_mobile_kmm.feature.address.domain.model.AddressResponseModel
import com.exquisite.a_mobile_kmm.feature.address.domain.repository.AddressRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class CreateAddressUseCase(private val repository: AddressRepository) {
    suspend operator fun invoke(request: CreateAddressRequestDto): Flow<UseCaseResult<AddressResponseModel>> {
        return repository.createAddress(request).map { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data.responseCode == "00") {
                        UseCaseResult.Success(result.data.toAddressResponseModel())
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
