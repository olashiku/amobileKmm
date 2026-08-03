package com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.mobile_toilet_event_toilet_form_two

import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.StandardToiletModel
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.ToiletPriceModel

sealed class EventToiletFormState {
    data object Idle : EventToiletFormState()
    data object Loading : EventToiletFormState()
    data class PriceSuccess(val price: ToiletPriceModel) : EventToiletFormState()
    data class Error(val message: String) : EventToiletFormState()
}
