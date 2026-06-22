package com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.event_toilet_checkout

import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.EventTypeModel
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.ToiletAvailabilityModel
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.ToiletPaymentModel
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.ToiletResponseModel

sealed class EventToiletCheckoutState {
    data object Idle : EventToiletCheckoutState()
    data object Loading : EventToiletCheckoutState()
    data class InitPaymentSuccess(val payment: ToiletPaymentModel) : EventToiletCheckoutState()
    data class CompletePaymentSuccess(val response: ToiletResponseModel) : EventToiletCheckoutState()
    data class DebitAccountSuccess(val response: ToiletResponseModel) : EventToiletCheckoutState()
    data class EventTypesSuccess(val eventTypes: List<EventTypeModel>) : EventToiletCheckoutState()
    data class AvailabilitySuccess(val availability: ToiletAvailabilityModel) : EventToiletCheckoutState()
    data class Error(val message: String) : EventToiletCheckoutState()
}
