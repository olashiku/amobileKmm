package com.exquisite.a_mobile_kmm.feature.employee.presenter.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.feature.employee.domain.model.BookingUpdateType
import com.exquisite.a_mobile_kmm.feature.employee.domain.model.UpdateAgentBookingRequest
import com.exquisite.a_mobile_kmm.feature.employee.domain.usecase.GetAgentBookingsUseCase
import com.exquisite.a_mobile_kmm.feature.employee.domain.usecase.UpdateAgentBookingUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateBookingViewModel(
    private val dataStore: AMobileDataStore,
    private val updateAgentBookingUseCase: UpdateAgentBookingUseCase,
    private val getAgentBookingsUseCase: GetAgentBookingsUseCase
) : ViewModel() {

    private val _updateState = MutableStateFlow<UpdateBookingUiState>(UpdateBookingUiState.Idle)
    val updateState = _updateState.asStateFlow()

    private val _bookingsListState = MutableStateFlow<BookingsListUiState>(BookingsListUiState.Initial)
    val bookingsListState = _bookingsListState.asStateFlow()

    private val _currentBookingType = MutableStateFlow("")
    val currentBookingType = _currentBookingType.asStateFlow()

    private var _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore = _isLoadingMore.asStateFlow()



    private var currentPage = -1  // Start at -1, so first page is 0
    private var isLastPage = false
    private var isInitialLoading = false




    fun fetchBookings(
        bookingType: String = _currentBookingType.value,
        pageNo: Int = 0,
        pageSize: Int = 10
    ) {
        viewModelScope.launch {
            _bookingsListState.value = BookingsListUiState.Loading
            _currentBookingType.value = bookingType

            // Get agent ID from datastore
            val agentId = dataStore.getUserId().first().toIntOrNull()

            if (agentId == null) {
                _bookingsListState.value = BookingsListUiState.Error("Agent ID not found")
                return@launch
            }

            getAgentBookingsUseCase(agentId, pageNo, pageSize, bookingType).collect { result ->
                _bookingsListState.value = when (result) {
                    is Result.Success -> {
                        BookingsListUiState.Success(result.data)
                    }
                    is Result.Exception -> {
                        BookingsListUiState.Error(
                            result.exception.message ?: "Failed to fetch bookings"
                        )
                    }
                }
            }
        }
    }

    fun updateBooking(
        bookingId: Int,
        updateType: BookingUpdateType,
        agentRemark: String
    ) {
        viewModelScope.launch {
            _updateState.value = UpdateBookingUiState.Loading

            // Get employee ID from datastore
            val employeeId = dataStore.getUserId().first().toIntOrNull()

            if (employeeId == null) {
                _updateState.value = UpdateBookingUiState.Error("Employee ID not found")
                return@launch
            }

            val request = UpdateAgentBookingRequest(
                employeeId = employeeId,
                bookingId = bookingId,
                updateType = updateType,
                agentRemark = agentRemark
            )

            updateAgentBookingUseCase(request).collect { result ->
                _updateState.value = when (result) {
                    is Result.Success -> {
                        result.data?.let {
                            if (it.isSuccess) {
                                UpdateBookingUiState.Success(it.message)
                            } else {
                                UpdateBookingUiState.Error(it.message)
                            }
                        } ?: UpdateBookingUiState.Error("No response data")
                    }
                    is Result.Exception -> {
                        UpdateBookingUiState.Error(
                            result.exception.message ?: "An error occurred"
                        )
                    }
                }
            }
        }
    }


    fun resetUpdateState() {
        _updateState.value = UpdateBookingUiState.Idle
    }

    fun loadCustomerBookings( bookingType: String ,pageNo: Int = 0, pageSize: Int = 10,) {

        // Prevent duplicate initial loads
        if (isInitialLoading) return

        // Reset pagination state when loading page 0
        if (pageNo == 0) {
            currentPage = -1
            isLastPage = false
        }

        viewModelScope.launch {
            isInitialLoading = true
            _bookingsListState.value = BookingsListUiState.Loading

            try {
                val agentId = dataStore.getUserId().first().toIntOrNull()

                if (agentId == null) {
                    _bookingsListState.value = BookingsListUiState.Error("Agent ID not found")
                    isInitialLoading = false
                    return@launch
                }
                getAgentBookingsUseCase(agentId, pageNo, pageSize, bookingType).collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _bookingsListState.value = BookingsListUiState.Success(result.data)
                            currentPage = pageNo
                            isLastPage = result.data.size < pageSize
                        }
                        is Result.Exception -> {
                            _bookingsListState.value = BookingsListUiState.Error(
                                result.exception.message ?: "Failed to fetch bookings"
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _bookingsListState.value = BookingsListUiState.Error(e.message ?: "Unknown error")
            } finally {
                isInitialLoading = false
            }
        }
    }

    fun loadMoreBookings() {
        // Don't load more if already loading or reached the last page
        if (_isLoadingMore.value || isLastPage) return

        viewModelScope.launch {
            _isLoadingMore.value = true

            try {
                val nextPage = currentPage + 1
                val agentId = dataStore.getUserId().first().toIntOrNull()

                if (agentId == null) {
                    _isLoadingMore.value = false
                    return@launch
                }

                getAgentBookingsUseCase(agentId, nextPage, 10, _currentBookingType.value).collect { result ->
                    when (result) {
                        is Result.Success -> {
                            val currentBookings = (_bookingsListState.value as? BookingsListUiState.Success)?.bookings ?: emptyList()
                            val newBookings = currentBookings + result.data
                            _bookingsListState.value = BookingsListUiState.Success(newBookings)
                            currentPage = nextPage
                            isLastPage = result.data.size < 10
                        }
                        is Result.Exception -> {
                            // Keep current state, just stop loading
                        }
                    }
                }
            } catch (e: Exception) {
                // Keep current state, just stop loading
            } finally {
                _isLoadingMore.value = false
            }
        }
    }
}
