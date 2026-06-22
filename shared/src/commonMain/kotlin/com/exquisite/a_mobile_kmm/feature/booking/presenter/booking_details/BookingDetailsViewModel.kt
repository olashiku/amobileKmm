package com.exquisite.a_mobile_kmm.feature.booking.presenter.booking_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.request.RateAndReviewRequestDto
import com.exquisite.a_mobile_kmm.feature.booking.domain.usecase.GetCleaningBookingUseCase
import com.exquisite.a_mobile_kmm.feature.booking.domain.usecase.GetPestControlBookingUseCase
import com.exquisite.a_mobile_kmm.feature.booking.domain.usecase.GetSepticBookingUseCase
import com.exquisite.a_mobile_kmm.feature.booking.domain.usecase.GetToiletBookingUseCase
import com.exquisite.a_mobile_kmm.feature.booking.domain.usecase.RateAndReviewUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookingDetailsViewModel(
    private val getCleaningBookingUseCase: GetCleaningBookingUseCase,
    private val getSepticBookingUseCase: GetSepticBookingUseCase,
    private val getPestControlBookingUseCase: GetPestControlBookingUseCase,
    private val getToiletBookingUseCase: GetToiletBookingUseCase,
    private val rateAndReviewUseCase: RateAndReviewUseCase
) : ViewModel() {

    private var _bookingDetailsState = MutableStateFlow<BookingDetailsState>(BookingDetailsState.Idle)
    val bookingDetailsState = _bookingDetailsState.asStateFlow()

    fun loadCleaningBooking(bookingId: Int) {
        viewModelScope.launch {
            _bookingDetailsState.value = BookingDetailsState.Loading
            getCleaningBookingUseCase.invoke(bookingId).collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _bookingDetailsState.value = BookingDetailsState.CleaningBookingSuccess(response.data)
                    is UseCaseResult.Error ->
                        _bookingDetailsState.value = BookingDetailsState.Error(response.message)
                }
            }
        }
    }

    fun loadSepticBooking(bookingId: Int) {
        viewModelScope.launch {
            _bookingDetailsState.value = BookingDetailsState.Loading
            getSepticBookingUseCase.invoke(bookingId).collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _bookingDetailsState.value = BookingDetailsState.SepticBookingSuccess(response.data)
                    is UseCaseResult.Error ->
                        _bookingDetailsState.value = BookingDetailsState.Error(response.message)
                }
            }
        }
    }

    fun loadPestControlBooking(bookingId: Int) {
        viewModelScope.launch {
            _bookingDetailsState.value = BookingDetailsState.Loading
            getPestControlBookingUseCase.invoke(bookingId).collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _bookingDetailsState.value = BookingDetailsState.PestControlBookingSuccess(response.data)
                    is UseCaseResult.Error ->
                        _bookingDetailsState.value = BookingDetailsState.Error(response.message)
                }
            }
        }
    }

    fun loadToiletBooking(bookingId: Int) {
        viewModelScope.launch {
            _bookingDetailsState.value = BookingDetailsState.Loading
            getToiletBookingUseCase.invoke(bookingId).collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _bookingDetailsState.value = BookingDetailsState.ToiletBookingSuccess(response.data)
                    is UseCaseResult.Error ->
                        _bookingDetailsState.value = BookingDetailsState.Error(response.message)
                }
            }
        }
    }

    fun rateAndReview(request: RateAndReviewRequestDto) {
        viewModelScope.launch {
            _bookingDetailsState.value = BookingDetailsState.Loading
            rateAndReviewUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _bookingDetailsState.value = BookingDetailsState.RateReviewSuccess(response.data)
                    is UseCaseResult.Error ->
                        _bookingDetailsState.value = BookingDetailsState.Error(response.message)
                }
            }
        }
    }

    fun clearState() {
        _bookingDetailsState.value = BookingDetailsState.Idle
    }
}
