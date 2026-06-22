package com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.event_toilet_checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.remote.request.CheckToiletAvailabilityRequestDto
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.remote.request.CompleteToiletPaymentRequestDto
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.remote.request.DebitFromAccountRequestDto
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.remote.request.InitToiletPaymentRequestDto
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.usecase.CheckToiletAvailabilityUseCase
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.usecase.CompleteToiletPaymentUseCase
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.usecase.DebitFromAccountUseCase
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.usecase.GetEventTypeUseCase
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.usecase.InitToiletPaymentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EventToiletCheckoutViewModel(
    private val initToiletPaymentUseCase: InitToiletPaymentUseCase,
    private val completeToiletPaymentUseCase: CompleteToiletPaymentUseCase,
    private val debitFromAccountUseCase: DebitFromAccountUseCase,
    private val getEventTypeUseCase: GetEventTypeUseCase,
    private val checkToiletAvailabilityUseCase: CheckToiletAvailabilityUseCase
) : ViewModel() {

    private var _eventToiletCheckoutState = MutableStateFlow<EventToiletCheckoutState>(EventToiletCheckoutState.Idle)
    val eventToiletCheckoutState = _eventToiletCheckoutState.asStateFlow()

    fun initPayment(request: InitToiletPaymentRequestDto) {
        viewModelScope.launch {
            _eventToiletCheckoutState.value = EventToiletCheckoutState.Loading
            initToiletPaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _eventToiletCheckoutState.value = EventToiletCheckoutState.InitPaymentSuccess(response.data)
                    is UseCaseResult.Error -> _eventToiletCheckoutState.value = EventToiletCheckoutState.Error(response.message)
                }
            }
        }
    }

    fun completePayment(request: CompleteToiletPaymentRequestDto) {
        viewModelScope.launch {
            _eventToiletCheckoutState.value = EventToiletCheckoutState.Loading
            completeToiletPaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _eventToiletCheckoutState.value = EventToiletCheckoutState.CompletePaymentSuccess(response.data)
                    is UseCaseResult.Error -> _eventToiletCheckoutState.value = EventToiletCheckoutState.Error(response.message)
                }
            }
        }
    }

    fun debitFromAccount(request: DebitFromAccountRequestDto) {
        viewModelScope.launch {
            _eventToiletCheckoutState.value = EventToiletCheckoutState.Loading
            debitFromAccountUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _eventToiletCheckoutState.value = EventToiletCheckoutState.DebitAccountSuccess(response.data)
                    is UseCaseResult.Error -> _eventToiletCheckoutState.value = EventToiletCheckoutState.Error(response.message)
                }
            }
        }
    }

    fun getEventTypes() {
        viewModelScope.launch {
            _eventToiletCheckoutState.value = EventToiletCheckoutState.Loading
            getEventTypeUseCase.invoke().collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _eventToiletCheckoutState.value = EventToiletCheckoutState.EventTypesSuccess(response.data)
                    is UseCaseResult.Error -> _eventToiletCheckoutState.value = EventToiletCheckoutState.Error(response.message)
                }
            }
        }
    }

    fun checkAvailability(request: CheckToiletAvailabilityRequestDto) {
        viewModelScope.launch {
            _eventToiletCheckoutState.value = EventToiletCheckoutState.Loading
            checkToiletAvailabilityUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _eventToiletCheckoutState.value = EventToiletCheckoutState.AvailabilitySuccess(response.data)
                    is UseCaseResult.Error -> _eventToiletCheckoutState.value = EventToiletCheckoutState.Error(response.message)
                }
            }
        }
    }
}
