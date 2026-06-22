package com.exquisite.a_mobile_kmm.feature.booking.presenter.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.booking.domain.usecase.GetCustomerBookingsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookingViewModel(private val getCustomerBookingsUseCase: GetCustomerBookingsUseCase) : ViewModel() {

    private var _bookingState = MutableStateFlow<BookingState>(BookingState.Idle)
    val bookingState = _bookingState.asStateFlow()

    fun loadCustomerBookings(customerId: Int, pageNo: Int, pageSize: Int) {
        viewModelScope.launch {
            _bookingState.value = BookingState.Loading
            getCustomerBookingsUseCase.invoke(customerId, pageNo, pageSize).collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _bookingState.value = BookingState.Success(response.data)
                    is UseCaseResult.Error ->
                        _bookingState.value = BookingState.Error(response.message)
                }
            }
        }
    }

    fun clearState() {
        _bookingState.value = BookingState.Idle
    }
}
