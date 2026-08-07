package com.exquisite.a_mobile_kmm.feature.order.presenter.order_listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.order.domain.model.CustomerOrdersModel
import com.exquisite.a_mobile_kmm.feature.order.domain.usecase.GetCustomerOrdersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class OrderListingViewModel(private val getCustomerOrdersUseCase: GetCustomerOrdersUseCase,
    private val dataStore: AMobileDataStore
) : ViewModel() {

    private var _orderListingState = MutableStateFlow<OrderListingState>(OrderListingState.Idle)
    val orderListingState = _orderListingState.asStateFlow()

    private var currentPage = 0
    private val pageSize = 10
    private var isLoadingMore = false
    private var hasMorePages = true
    private val accumulatedOrders = mutableListOf<com.exquisite.a_mobile_kmm.feature.order.domain.model.CustomerOrder>()

    fun loadCustomerOrders(pageNumber: Int = 0, pageSize: Int = 10) {
        viewModelScope.launch {
            try {
                println("🔵 OrderListingVM: Starting load for page $pageNumber")
                val customerId = dataStore.getUserId().first().toInt()
                println("🔵 OrderListingVM: Customer ID = $customerId")

                // Initial load
                if (pageNumber == 0) {
                    currentPage = 0
                    accumulatedOrders.clear()
                    hasMorePages = true
                    println("🔵 OrderListingVM: Setting state to Loading")
                    _orderListingState.value = OrderListingState.Loading
                }

                println("🔵 OrderListingVM: Calling API...")
                val response = withTimeout(30000L) { // 30 second timeout
                    getCustomerOrdersUseCase.invoke(customerId, pageNumber, pageSize).first()
                }
                println("🔵 OrderListingVM: API response received")

                // Always stop loading flags
                isLoadingMore = false

                when (response) {
                    is UseCaseResult.Success -> {
                        val newOrders = response.data.orders
                        println("🟢 OrderListingVM: Success! Received ${newOrders.size} orders")

                        // If empty result, stop pagination immediately
                        if (newOrders.isEmpty()) {
                            println("⚠️ OrderListingVM: Empty result - stopping pagination")
                            hasMorePages = false

                            // FORCE update state to Success with empty orders - use direct constructor
                            println("🟢 OrderListingVM: Setting state to Success with 0 orders")
                            _orderListingState.value = OrderListingState.Success(
                                data = CustomerOrdersModel(orders = accumulatedOrders.toList()),
                                hasMore = false,
                                isLoadingMore = false
                            )
                            println("✅ OrderListingVM: State updated successfully")
                            return@launch
                        }

                        // Add new orders to accumulated list
                        if (pageNumber == 0) {
                            accumulatedOrders.clear()
                        }
                        accumulatedOrders.addAll(newOrders)

                        // Check if there are more pages
                        hasMorePages = newOrders.size >= pageSize

                        // Update state with accumulated orders - use direct constructor
                        _orderListingState.value = OrderListingState.Success(
                            data = CustomerOrdersModel(orders = accumulatedOrders.toList()),
                            hasMore = hasMorePages,
                            isLoadingMore = false
                        )
                    }
                    is UseCaseResult.Error -> {
                        hasMorePages = false

                        if (pageNumber == 0) {
                            _orderListingState.value = OrderListingState.Error(response.message)
                        } else {
                            // Keep current state for pagination errors
                            val currentState = _orderListingState.value
                            if (currentState is OrderListingState.Success) {
                                _orderListingState.value = currentState.copy(
                                    hasMore = false,
                                    isLoadingMore = false
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                // ALWAYS stop loading on any exception
                isLoadingMore = false
                hasMorePages = false

                if (pageNumber == 0 || _orderListingState.value is OrderListingState.Loading) {
                    _orderListingState.value = OrderListingState.Error(e.message ?: "Unknown error occurred")
                } else {
                    // Keep current state but stop loading
                    val currentState = _orderListingState.value
                    if (currentState is OrderListingState.Success) {
                        _orderListingState.value = currentState.copy(
                            hasMore = false,
                            isLoadingMore = false
                        )
                    }
                }
            }
        }
    }

    fun loadNextPage() {
        if (!isLoadingMore && hasMorePages) {
            isLoadingMore = true
            currentPage++

            // Update state to show loading more indicator
            val currentState = _orderListingState.value
            if (currentState is OrderListingState.Success) {
                _orderListingState.value = currentState.copy(isLoadingMore = true)
            }

            loadCustomerOrders(currentPage, pageSize)
        }
    }

    fun clearState() {
        _orderListingState.value = OrderListingState.Idle
        currentPage = 0
        accumulatedOrders.clear()
        hasMorePages = true
        isLoadingMore = false
    }
}
