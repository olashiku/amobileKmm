package com.exquisite.a_mobile_kmm.feature.order.data.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.safeApiCall
import com.exquisite.a_mobile_kmm.feature.order.data.remote.response.GetCustomerOrdersResponseDto
import com.exquisite.a_mobile_kmm.feature.order.domain.repository.OrderRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow

class OrderRepositoryImpl(private val httpClient: HttpClient) : OrderRepository {

    override suspend fun getCustomerOrders(
        customerId: Int,
        pageNumber: Int,
        pageSize: Int
    ): Flow<Result<GetCustomerOrdersResponseDto>> {
        return safeApiCall {
            httpClient.get("api/v1/order/get_customer_orders") {
                parameter("customerId", customerId)
                parameter("pageNumber", pageNumber)
                parameter("pageSize", pageSize)
            }
        }
    }
}
