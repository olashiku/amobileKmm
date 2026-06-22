package com.exquisite.a_mobile_kmm.feature.order.domain.usecase

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.handleException
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.order.data.mapper.toCustomerOrdersModel
import com.exquisite.a_mobile_kmm.feature.order.domain.model.CustomerOrdersModel
import com.exquisite.a_mobile_kmm.feature.order.domain.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GetCustomerOrdersUseCase(private val orderRepository: OrderRepository) {

    suspend operator fun invoke(
        customerId: Int,
        pageNumber: Int,
        pageSize: Int
    ): Flow<UseCaseResult<CustomerOrdersModel>> {
        return orderRepository.getCustomerOrders(customerId, pageNumber, pageSize).map { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data.responseCode == "00") {
                        val customerOrdersModel = result.data.toCustomerOrdersModel()
                        if (customerOrdersModel != null) {
                            UseCaseResult.Success(customerOrdersModel)
                        } else {
                            UseCaseResult.Error("Invalid orders response data")
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
