package com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.mobile_toilet_event_toilet_form_two

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.screen_components.DateModel
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.EventToiletFormTwoModel
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.GetToiletPriceRequestModel
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.usecase.GetStandardToiletsListUseCase
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.usecase.GetToiletPriceUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EventToiletFormViewModel(
    private val getToiletPriceUseCase: GetToiletPriceUseCase,
    private val getStandardToiletsListUseCase: GetStandardToiletsListUseCase,
) : ViewModel() {

    private var _eventToiletFormState = MutableStateFlow<EventToiletFormState>(EventToiletFormState.Idle)
    val eventToiletFormState = _eventToiletFormState.asStateFlow()

    private val _eventToiletFormTwoModel = MutableStateFlow(EventToiletFormTwoModel())
    val eventToiletFormTwoModel = _eventToiletFormTwoModel.asStateFlow()

    fun setStartTime(eventStartTime: String) {
        _eventToiletFormTwoModel.value =
            _eventToiletFormTwoModel.value.copy(eventStartTime = eventStartTime)
    }

    fun setEndTime(eventEndTime: String) {
        _eventToiletFormTwoModel.value =
            _eventToiletFormTwoModel.value.copy(eventEndTime = eventEndTime)
    }

    fun setStartDate(eventStartDate: DateModel?) {
        _eventToiletFormTwoModel.value =
            _eventToiletFormTwoModel.value.copy(eventStartDate = eventStartDate)
    }

    fun setEndDate(eventEndDate: DateModel?) {
        _eventToiletFormTwoModel.value =
            _eventToiletFormTwoModel.value.copy(eventEndDate = eventEndDate)
    }

    fun setNumberOfGuest(numberOfGuest: String) {
        _eventToiletFormTwoModel.value =
            _eventToiletFormTwoModel.value.copy(numberOfGuest = numberOfGuest)
    }

    fun setNumberOfStandardToilet(numberOfStandardToilet: String) {
        _eventToiletFormTwoModel.value =
            _eventToiletFormTwoModel.value.copy(numberOfStandardToilet = numberOfStandardToilet)
    }

    fun setNumberOfVipToilet(numberOfVipToilet: String) {
        _eventToiletFormTwoModel.value =
            _eventToiletFormTwoModel.value.copy(numberOfVipToilet = numberOfVipToilet)
    }

    fun getToiletPrice(
        maximumNumberOfGuest: String,
        serviceType: String,
        numberOfStandardToilet: String,
        numberOfVipToilets: String,
        eventStartDate: String,
        eventEndDate: String,
        eventStartTime: String,
        eventEndTime: String
    ) {
        viewModelScope.launch {
            val request = GetToiletPriceRequestModel(
                "0", maximumNumberOfGuest, serviceType,
                numberOfStandardToilet, numberOfVipToilets, eventStartDate,
                eventEndDate, eventStartTime, eventEndTime
            )
            _eventToiletFormState.value = EventToiletFormState.Loading
            getToiletPriceUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _eventToiletFormState.value =
                        EventToiletFormState.PriceSuccess(response.data)

                    is UseCaseResult.Error -> _eventToiletFormState.value =
                        EventToiletFormState.Error(response.message)
                }
            }
        }
    }

    fun clearState(){
        _eventToiletFormState.value = EventToiletFormState.Idle
    }
}
