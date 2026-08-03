package com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.mobile_toilet_event_form_one

import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.ToiletAvailabilityModel

sealed class ToiletAvailabilityState {
    data object Idle : ToiletAvailabilityState()
    data object Loading : ToiletAvailabilityState()
    data class Success(val data: ToiletAvailabilityModel) : ToiletAvailabilityState()
    data class Error(val message: String) : ToiletAvailabilityState()
}