package com.exquisite.a_mobile_kmm.feature.booking.presenter.booking

import com.exquisite.a_mobile_kmm.feature.booking.domain.model.CustomerBookingsModel

sealed class BookingState {
    data object Idle : BookingState()
    data object Loading : BookingState()
    data class Success(val data: CustomerBookingsModel) : BookingState()
    data class Error(val message: String) : BookingState()
}
