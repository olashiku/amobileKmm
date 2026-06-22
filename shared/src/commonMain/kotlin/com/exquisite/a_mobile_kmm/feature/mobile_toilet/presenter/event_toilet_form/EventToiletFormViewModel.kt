package com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.event_toilet_form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.remote.request.GetToiletPriceRequestDto
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.usecase.GetStandardToiletsListUseCase
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.usecase.GetToiletPriceUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EventToiletFormViewModel(
    private val getToiletPriceUseCase: GetToiletPriceUseCase,
    private val getStandardToiletsListUseCase: GetStandardToiletsListUseCase
) : ViewModel() {

    private var _eventToiletFormState = MutableStateFlow<EventToiletFormState>(EventToiletFormState.Idle)
    val eventToiletFormState = _eventToiletFormState.asStateFlow()

    fun getToiletPrice(request: GetToiletPriceRequestDto) {
        viewModelScope.launch {
            _eventToiletFormState.value = EventToiletFormState.Loading
            getToiletPriceUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _eventToiletFormState.value = EventToiletFormState.PriceSuccess(response.data)
                    is UseCaseResult.Error -> _eventToiletFormState.value = EventToiletFormState.Error(response.message)
                }
            }
        }
    }

    fun getStandardToiletsList() {
        viewModelScope.launch {
            _eventToiletFormState.value = EventToiletFormState.Loading
            getStandardToiletsListUseCase.invoke().collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _eventToiletFormState.value = EventToiletFormState.StandardToiletsListSuccess(response.data)
                    is UseCaseResult.Error -> _eventToiletFormState.value = EventToiletFormState.Error(response.message)
                }
            }
        }
    }
}
