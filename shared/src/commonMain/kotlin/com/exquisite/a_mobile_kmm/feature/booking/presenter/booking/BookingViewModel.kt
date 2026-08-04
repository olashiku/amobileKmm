package com.exquisite.a_mobile_kmm.feature.booking.presenter.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.booking.domain.usecase.GetCustomerBookingsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BookingViewModel(private val getCustomerBookingsUseCase: GetCustomerBookingsUseCase,
    private val dataStore: AMobileDataStore) : ViewModel() {

    private var _bookingState = MutableStateFlow<BookingState>(BookingState.Idle)
    val bookingState = _bookingState.asStateFlow()

    private var _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore = _isLoadingMore.asStateFlow()

    private var currentPage = 0
    private var isLastPage = false


    fun loadCustomerBookings(pageNo: Int = 0, pageSize: Int = 10) {
        viewModelScope.launch {
            val customerId = dataStore.getUserId().first().toInt()
            _bookingState.value = BookingState.Loading
            getCustomerBookingsUseCase.invoke(customerId, pageNo, pageSize).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> {
                        _bookingState.value = BookingState.Success(response.data)
                        currentPage = pageNo
                        isLastPage = response.data.bookings.size < pageSize
                    }
                    is UseCaseResult.Error ->
                        _bookingState.value = BookingState.Error(response.message)
                }
            }
        }
    }

    fun loadMoreBookings(pageSize: Int = 10) {
        if (_isLoadingMore.value || isLastPage) return

        viewModelScope.launch {
            val customerId = dataStore.getUserId().first().toInt()
            _isLoadingMore.value = true
            val nextPage = currentPage + 1

            getCustomerBookingsUseCase.invoke(customerId, nextPage, pageSize).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> {
                        val currentState = _bookingState.value
                        if (currentState is BookingState.Success) {
                            val updatedBookings = currentState.data.bookings + response.data.bookings
                            _bookingState.value = BookingState.Success(
                                response.data.copy(bookings = updatedBookings)
                            )
                        }
                        currentPage = nextPage
                        isLastPage = response.data.bookings.size < pageSize
                        _isLoadingMore.value = false
                    }
                    is UseCaseResult.Error -> {
                        _isLoadingMore.value = false
                    }
                }
            }
        }
    }

    fun clearState() {
        _bookingState.value = BookingState.Idle
        currentPage = 0
        isLastPage = false
    }
}
