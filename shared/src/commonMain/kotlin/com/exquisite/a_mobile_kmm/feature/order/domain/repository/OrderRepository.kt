package com.exquisite.a_mobile_kmm.feature.order.domain.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.feature.order.data.remote.response.GetCustomerOrdersResponseDto
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun getCustomerOrders(customerId: Int, pageNumber: Int, pageSize: Int): Flow<Result<GetCustomerOrdersResponseDto>>
}
