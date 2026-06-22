package com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.event_toilet_form

import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.StandardToiletModel
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.ToiletPriceModel

sealed class EventToiletFormState {
    data object Idle : EventToiletFormState()
    data object Loading : EventToiletFormState()
    data class PriceSuccess(val price: ToiletPriceModel) : EventToiletFormState()
    data class StandardToiletsListSuccess(val list: List<StandardToiletModel>) : EventToiletFormState()
    data class Error(val message: String) : EventToiletFormState()
}
