package com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.mobile_toilet_event_form_one

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.screen_components.DateModel
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.CheckToiletAvailabilityRequestModel
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.MobileToiletEventFormOneFormData
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.ToiletAvailabilityModel
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.usecase.CheckToiletAvailabilityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MobileToiletEventFormOneViewModel(
    private val checkToiletAvailabilityUseCase: CheckToiletAvailabilityUseCase
): ViewModel() {

    private val _formData = MutableStateFlow(MobileToiletEventFormOneFormData())
    val formData = _formData.asStateFlow()

    private val _availabilityState = MutableStateFlow<ToiletAvailabilityState>(ToiletAvailabilityState.Idle)
    val availabilityState = _availabilityState.asStateFlow()

    fun setSelectedDate(dateModel:DateModel?){
        _formData.value.eventDate =  dateModel
    }

    fun setSelectionOption(selectionOption:String){
        _formData.value.selectedCleaningOption = selectionOption
    }

    fun checkToiletAvailability(serviceType: String, eventDate: String) {
        viewModelScope.launch {
            val request = CheckToiletAvailabilityRequestModel(
                serviceType = serviceType,
                eventDate = eventDate
            )
            _availabilityState.value = ToiletAvailabilityState.Loading
            checkToiletAvailabilityUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _availabilityState.value = ToiletAvailabilityState.Success(response.data)
                    is UseCaseResult.Error -> _availabilityState.value = ToiletAvailabilityState.Error(response.message)
                }
            }
        }
    }

    fun resetAvailabilityState() {
        _availabilityState.value = ToiletAvailabilityState.Idle
    }
}

