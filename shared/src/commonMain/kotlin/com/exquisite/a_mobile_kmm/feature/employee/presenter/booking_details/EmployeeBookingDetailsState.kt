package com.exquisite.a_mobile_kmm.feature.employee.presenter.booking_details

import com.exquisite.a_mobile_kmm.feature.booking.domain.model.CleaningBookingModel
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.PestControlBookingModel
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.RateReviewModel
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.SepticBookingModel
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.ToiletBookingModel

sealed class BookingDetailsState {
    data object Idle : BookingDetailsState()
    data object Loading : BookingDetailsState()
    data class CleaningBookingSuccess(val data: CleaningBookingModel) : BookingDetailsState()
    data class SepticBookingSuccess(val data: SepticBookingModel) : BookingDetailsState()
    data class PestControlBookingSuccess(val data: PestControlBookingModel) : BookingDetailsState()
    data class ToiletBookingSuccess(val data: ToiletBookingModel) : BookingDetailsState()
    data class Error(val message: String) : BookingDetailsState()
}

sealed class RateReviewState{
    data object Idle : RateReviewState()
    data object Loading : RateReviewState()
   data class RateReviewSuccess(val data: RateReviewModel) : RateReviewState()
    data class Error(val message: String) : RateReviewState()
}

sealed class UpdateBookingImagesState {
    data object Idle : UpdateBookingImagesState()
    data object Loading : UpdateBookingImagesState()
    data class Success(val message: String) : UpdateBookingImagesState()
    data class Error(val message: String) : UpdateBookingImagesState()
}
