package com.exquisite.a_mobile_kmm.feature.employee.presenter.booking

import com.exquisite.a_mobile_kmm.feature.employee.domain.model.AgentBooking

sealed class UpdateBookingUiState {
    data object Idle : UpdateBookingUiState()
    data object Loading : UpdateBookingUiState()
    data class Success(val message: String) : UpdateBookingUiState()
    data class Error(val message: String) : UpdateBookingUiState()
}

sealed class BookingsListUiState {
    data object Initial : BookingsListUiState()
    data object Loading : BookingsListUiState()
    data class Success(val bookings: List<AgentBooking>) : BookingsListUiState()
    data class Error(val message: String) : BookingsListUiState()
}
