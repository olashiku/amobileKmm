package com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.event_toilet_checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.CompleteToiletPaymentRequestModel
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.DebitFromAccountRequestModel
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.InitToiletPaymentRequestModel
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.usecase.CompleteToiletPaymentUseCase
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.usecase.DebitFromAccountUseCase
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.usecase.InitToiletPaymentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EventToiletCheckoutViewModel(
    private val initToiletPaymentUseCase: InitToiletPaymentUseCase,
    private val completeToiletPaymentUseCase: CompleteToiletPaymentUseCase,
    private val debitFromAccountUseCase: DebitFromAccountUseCase,
    private val dataStore: AMobileDataStore
) : ViewModel() {

    private var _eventToiletCheckoutState =
        MutableStateFlow<EventToiletCheckoutState>(EventToiletCheckoutState.Idle)
    val eventToiletCheckoutState = _eventToiletCheckoutState.asStateFlow()

    private var _refState = MutableStateFlow("")


    fun initPayment(
        uniqueRef: String,
        contactPersonName: String,
        contactPersonPhone: String,
        contactPersonEmail: String,
        address: String,
        typeOfEvent: String,
        extraNote: String,
        pictureOfEventLocation: List<String>,
        pictureOfToiletPlacement: List<String>,
        companyName: String,
        companyEmail: String
    ) {
        viewModelScope.launch {
            val userId = dataStore.getUserId().first()

            val request = InitToiletPaymentRequestModel(
                uniqueRef = uniqueRef,
                contactPersonName = contactPersonName,
                contactPersonPhone = contactPersonPhone,
                contactPersonEmail = contactPersonEmail,
                address = address,
                typeOfEvent = typeOfEvent,
                extraNote = extraNote,
                customerId = userId.toInt(),
                pictureOfEventLocation = pictureOfEventLocation,
                pictureOfToiletPlacement = pictureOfToiletPlacement,
                companyName = companyName,
                companyEmail = companyEmail
            )
            _eventToiletCheckoutState.value = EventToiletCheckoutState.Loading
            initToiletPaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> {
                        _refState.value = response.data.first
                        _eventToiletCheckoutState.value =
                            EventToiletCheckoutState.InitPaymentSuccess(response.data)
                    }

                    is UseCaseResult.Error -> {
                        _eventToiletCheckoutState.value =
                            EventToiletCheckoutState.Error(response.message)
                    }
                }
            }
        }
    }

    fun completePayment(txnRef: String) {
        viewModelScope.launch {
            val userId = dataStore.getUserId().first()
            val request = CompleteToiletPaymentRequestModel(
                customerId = userId.toInt(),
                ref = _refState.value,
                txnRef = txnRef
            )

            _eventToiletCheckoutState.value = EventToiletCheckoutState.Loading
            completeToiletPaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _eventToiletCheckoutState.value =
                        EventToiletCheckoutState.CompletePaymentSuccess(response.data)

                    is UseCaseResult.Error -> _eventToiletCheckoutState.value =
                        EventToiletCheckoutState.Error(response.message)
                }
            }
        }
    }

    fun debitFromAccount(
        uniqueRef: String,
        contactPersonName: String,
        contactPersonPhone: String,
        contactPersonEmail: String,
        address: String,
        typeOfEvent: String,
        extraNote: String,
        pictureOfEventLocation: List<String>,
        pictureOfToiletPlacement: List<String>,
        companyName: String,
        companyEmail: String
    ) {


        viewModelScope.launch {
            val userId = dataStore.getUserId().first()

            val request = DebitFromAccountRequestModel(
                uniqueRef = uniqueRef,
                contactPersonName = contactPersonName,
                contactPersonPhone = contactPersonPhone,
                contactPersonEmail = contactPersonEmail,
                address = address,
                typeOfEvent = typeOfEvent,
                extraNote = extraNote,
                customerId = userId.toInt(),
                pictureOfEventLocation = pictureOfEventLocation,
                pictureOfToiletPlacement = pictureOfToiletPlacement,
                companyName = companyName,
                companyEmail = companyEmail
            )
            _eventToiletCheckoutState.value = EventToiletCheckoutState.Loading
            debitFromAccountUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _eventToiletCheckoutState.value =
                        EventToiletCheckoutState.DebitAccountSuccess(response.data)

                    is UseCaseResult.Error -> _eventToiletCheckoutState.value =
                        EventToiletCheckoutState.Error(response.message)
                }
            }
        }
    }

    fun clearError() {
        _eventToiletCheckoutState.value = EventToiletCheckoutState.Idle
    }

    fun clearReference(){
        _refState.value = ""
    }

}
