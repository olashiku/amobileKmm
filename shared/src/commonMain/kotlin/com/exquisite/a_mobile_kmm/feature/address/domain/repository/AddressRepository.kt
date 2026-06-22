package com.exquisite.a_mobile_kmm.feature.address.domain.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.feature.address.data.remote.request.*
import com.exquisite.a_mobile_kmm.feature.address.data.remote.response.*
import kotlinx.coroutines.flow.Flow

interface AddressRepository {
    suspend fun getAddresses(customerId: Int): Flow<Result<GetAddressesResponseDto>>
    suspend fun updateAddress(request: UpdateAddressRequestDto): Flow<Result<UpdateAddressResponseDto>>
    suspend fun createAddress(request: CreateAddressRequestDto): Flow<Result<CreateAddressResponseDto>>
    suspend fun deleteAddress(request: DeleteAddressRequestDto): Flow<Result<DeleteAddressResponseDto>>
}
