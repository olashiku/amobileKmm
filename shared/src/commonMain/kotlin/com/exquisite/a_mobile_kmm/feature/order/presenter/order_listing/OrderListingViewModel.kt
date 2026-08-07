package com.exquisite.a_mobile_kmm.feature.order.presenter.order_listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.order.domain.usecase.GetCustomerOrdersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class OrderListingViewModel(private val getCustomerOrdersUseCase: GetCustomerOrdersUseCase,
    private val dataStore: AMobileDataStore
) : ViewModel() {

    private var _orderListingState = MutableStateFlow<OrderListingState>(OrderListingState.Idle)
    val orderListingState = _orderListingState.asStateFlow()

    fun loadCustomerOrders( pageNumber: Int, pageSize: Int) {
        viewModelScope.launch {
            val customerId = dataStore.getUserId().first().toInt()

            _orderListingState.value = OrderListingState.Loading
            getCustomerOrdersUseCase.invoke(customerId, pageNumber, pageSize).collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _orderListingState.value = OrderListingState.Success(response.data)
                    is UseCaseResult.Error ->
                        _orderListingState.value = OrderListingState.Error(response.message)
                }
            }
        }
    }

    fun clearState() {
        _orderListingState.value = OrderListingState.Idle
    }
}
