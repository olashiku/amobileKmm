package com.exquisite.a_mobile_kmm.feature.address.data.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.safeApiCall
import com.exquisite.a_mobile_kmm.feature.address.data.remote.request.*
import com.exquisite.a_mobile_kmm.feature.address.data.remote.response.*
import com.exquisite.a_mobile_kmm.feature.address.domain.repository.AddressRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow

class AddressRepositoryImpl(private val httpClient: HttpClient) : AddressRepository {

    override suspend fun getAddresses(customerId: Int): Flow<Result<GetAddressesResponseDto>> {
        return safeApiCall {
            httpClient.get("api/v1/customer/get_addresses?customerId=$customerId")
        }
    }

    override suspend fun updateAddress(request: UpdateAddressRequestDto): Flow<Result<UpdateAddressResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/customer/update_address") {
                setBody(request)
            }
        }
    }

    override suspend fun createAddress(request: CreateAddressRequestDto): Flow<Result<CreateAddressResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/customer/create_address") {
                setBody(request)
            }
        }
    }

    override suspend fun deleteAddress(request: DeleteAddressRequestDto): Flow<Result<DeleteAddressResponseDto>> {
        return safeApiCall {
            httpClient.delete("api/v1/customer/delete_address") {
                setBody(request)
            }
        }
    }
}
