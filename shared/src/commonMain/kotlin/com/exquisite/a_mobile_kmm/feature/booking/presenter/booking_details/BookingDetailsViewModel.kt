package com.exquisite.a_mobile_kmm.feature.booking.presenter.booking_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.RateAndReviewRequest
import com.exquisite.a_mobile_kmm.feature.booking.domain.usecase.GetCleaningBookingUseCase
import com.exquisite.a_mobile_kmm.feature.booking.domain.usecase.GetPestControlBookingUseCase
import com.exquisite.a_mobile_kmm.feature.booking.domain.usecase.GetSepticBookingUseCase
import com.exquisite.a_mobile_kmm.feature.booking.domain.usecase.GetToiletBookingUseCase
import com.exquisite.a_mobile_kmm.feature.booking.domain.usecase.RateAndReviewUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BookingDetailsViewModel(
    private val getCleaningBookingUseCase: GetCleaningBookingUseCase,
    private val getSepticBookingUseCase: GetSepticBookingUseCase,
    private val getPestControlBookingUseCase: GetPestControlBookingUseCase,
    private val getToiletBookingUseCase: GetToiletBookingUseCase,
    private val rateAndReviewUseCase: RateAndReviewUseCase,
    private val dataStore: AMobileDataStore
) : ViewModel() {

    private var _bookingDetailsState = MutableStateFlow<BookingDetailsState>(BookingDetailsState.Idle)
    val bookingDetailsState = _bookingDetailsState.asStateFlow()

     private val _rateReviewState = MutableStateFlow<RateReviewState>(RateReviewState.Idle)
    val rateReviewState = _rateReviewState.asStateFlow()

    // Cache to track already loaded booking IDs
    private var cachedBookingId: Int? = null
    private var cachedBookingType: String? = null

    fun loadCleaningBooking(bookingId: Int) {
        // Skip if already loaded
        if (cachedBookingId == bookingId && cachedBookingType == "CLEANING" &&
            _bookingDetailsState.value is BookingDetailsState.CleaningBookingSuccess) {
            return
        }

        viewModelScope.launch {
            _bookingDetailsState.value = BookingDetailsState.Loading
            try {
                val response = getCleaningBookingUseCase.invoke(bookingId).first()
                when (response) {
                    is UseCaseResult.Success -> {
                        _bookingDetailsState.value =
                            BookingDetailsState.CleaningBookingSuccess(response.data)
                        cachedBookingId = bookingId
                        cachedBookingType = "CLEANING"
                    }

                    is UseCaseResult.Error ->
                        _bookingDetailsState.value = BookingDetailsState.Error(response.message)
                }
            } catch (e: Exception) {
                _bookingDetailsState.value = BookingDetailsState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun loadSepticBooking(bookingId: Int) {
        // Skip if already loaded
        if (cachedBookingId == bookingId && cachedBookingType == "SEPTIC" &&
            _bookingDetailsState.value is BookingDetailsState.SepticBookingSuccess) {
            return
        }

        viewModelScope.launch {
            _bookingDetailsState.value = BookingDetailsState.Loading
            try {
                val response = getSepticBookingUseCase.invoke(bookingId).first()
                when (response) {
                    is UseCaseResult.Success -> {
                        _bookingDetailsState.value =
                            BookingDetailsState.SepticBookingSuccess(response.data)
                        cachedBookingId = bookingId
                        cachedBookingType = "SEPTIC"
                    }

                    is UseCaseResult.Error ->
                        _bookingDetailsState.value = BookingDetailsState.Error(response.message)
                }
            } catch (e: Exception) {
                _bookingDetailsState.value = BookingDetailsState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun loadPestControlBooking(bookingId: Int) {
        // Skip if already loaded
        if (cachedBookingId == bookingId && cachedBookingType == "PEST_CONTROL" &&
            _bookingDetailsState.value is BookingDetailsState.PestControlBookingSuccess) {
            return
        }

        viewModelScope.launch {
            _bookingDetailsState.value = BookingDetailsState.Loading
            try {
                val response = getPestControlBookingUseCase.invoke(bookingId).first()
                when (response) {
                    is UseCaseResult.Success -> {
                        _bookingDetailsState.value =
                            BookingDetailsState.PestControlBookingSuccess(response.data)
                        cachedBookingId = bookingId
                        cachedBookingType = "PEST_CONTROL"
                    }

                    is UseCaseResult.Error ->
                        _bookingDetailsState.value = BookingDetailsState.Error(response.message)
                }
            } catch (e: Exception) {
                _bookingDetailsState.value = BookingDetailsState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun loadToiletBooking(bookingId: Int) {
        // Skip if already loaded
        if (cachedBookingId == bookingId && cachedBookingType == "TOILET" &&
            _bookingDetailsState.value is BookingDetailsState.ToiletBookingSuccess) {
            return
        }

        viewModelScope.launch {
            _bookingDetailsState.value = BookingDetailsState.Loading
            try {
                val response = getToiletBookingUseCase.invoke(bookingId).first()
                when (response) {
                    is UseCaseResult.Success -> {
                        _bookingDetailsState.value =
                            BookingDetailsState.ToiletBookingSuccess(response.data)
                        cachedBookingId = bookingId
                        cachedBookingType = "TOILET"
                    }

                    is UseCaseResult.Error ->
                        _bookingDetailsState.value = BookingDetailsState.Error(response.message)
                }
            } catch (e: Exception) {
                _bookingDetailsState.value = BookingDetailsState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun rateAndReview(
        serviceType: String,
        comment: String,
        rate: Int,
        bookingId: Int
    ) {
        viewModelScope.launch {
            _rateReviewState.value = RateReviewState.Loading
            try {
                val customerId = dataStore.getUserId().first().toInt()

                val request = RateAndReviewRequest(
                    serviceType = serviceType,
                    comment = comment,
                    rate = rate,
                    customerId = customerId,
                    bookingId = bookingId
                )

                val response = rateAndReviewUseCase.invoke(request).first()
                when (response) {
                    is UseCaseResult.Success ->
                        _rateReviewState.value =
                            RateReviewState.RateReviewSuccess(response.data)

                    is UseCaseResult.Error ->
                        _rateReviewState.value = RateReviewState.Error(response.message)
                }
            } catch (e: Exception) {
                _rateReviewState.value = RateReviewState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun clearState() {
        _bookingDetailsState.value = BookingDetailsState.Idle
        cachedBookingId = null
        cachedBookingType = null
    }
}
