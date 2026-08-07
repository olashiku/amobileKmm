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

    private var currentPage = -1  // Start at -1, so first page is 0
    private var isLastPage = false
    private var isInitialLoading = false


    fun loadCustomerBookings(pageNo: Int = 0, pageSize: Int = 10) {
        // Prevent duplicate initial loads
        if (isInitialLoading) return

        // Reset pagination state when loading page 0
        if (pageNo == 0) {
            currentPage = -1
            isLastPage = false
        }

        viewModelScope.launch {
            isInitialLoading = true
            _bookingState.value = BookingState.Loading
            try {
                val customerId = dataStore.getUserId().first().toInt()
                val response = getCustomerBookingsUseCase.invoke(customerId, pageNo, pageSize).first()

                when (response) {
                    is UseCaseResult.Success -> {
                        _bookingState.value = BookingState.Success(response.data)
                        currentPage = pageNo
                        isLastPage = response.data.bookings.size < pageSize
                    }
                    is UseCaseResult.Error ->
                        _bookingState.value = BookingState.Error(response.message)
                }
            } catch (e: Exception) {
                _bookingState.value = BookingState.Error(e.message ?: "Unknown error")
            } finally {
                isInitialLoading = false
            }
        }
    }

    fun loadMoreBookings(pageSize: Int = 10) {
        // Don't paginate if still loading initial data, already loading more, reached last page, or no initial page loaded yet
        if (isInitialLoading || _isLoadingMore.value || isLastPage || currentPage < 0) return

        viewModelScope.launch {
            _isLoadingMore.value = true
            try {
                val customerId = dataStore.getUserId().first().toInt()
                val nextPage = currentPage + 1

                val response = getCustomerBookingsUseCase.invoke(customerId, nextPage, pageSize).first()

                when (response) {
                    is UseCaseResult.Success -> {
                        val currentState = _bookingState.value
                        if (currentState is BookingState.Success) {
                            val updatedBookings = currentState.data.bookings + response.data.bookings
                            _bookingState.value = BookingState.Success(
                                response.data.copy(bookings = updatedBookings)
                            )
                        }
                        // ALWAYS update these, even if state isn't Success yet
                        currentPage = nextPage
                        isLastPage = response.data.bookings.size < pageSize
                    }
                    is UseCaseResult.Error -> {
                        // Mark as last page on error to prevent infinite retries
                        isLastPage = true
                    }
                }
            } catch (e: Exception) {
                // Handle exception - stop trying to load more on error
                isLastPage = true
            } finally {
                _isLoadingMore.value = false
            }
        }
    }

    fun clearState() {
        _bookingState.value = BookingState.Idle
        currentPage = -1
        isLastPage = false
        isInitialLoading = false
    }
}
