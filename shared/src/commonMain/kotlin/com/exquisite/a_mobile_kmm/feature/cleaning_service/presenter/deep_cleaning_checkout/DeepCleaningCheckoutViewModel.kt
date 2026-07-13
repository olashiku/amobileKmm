package com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.deep_cleaning_checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.CompleteDeepCleaningPaymentRequest
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.DebitFromWalletDeepCleaningPaymentRequest
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.InitDeepCleaningPaymentRequest
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.CompleteDeepCleaningPaymentUseCase
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.DebitFromWalletDeepCleaningPaymentUseCase
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.InitDeepCleaningPaymentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.invoke

class DeepCleaningCheckoutViewModel(
    private val initDeepCleaningPaymentUseCase: InitDeepCleaningPaymentUseCase,
    private val completeDeepCleaningPaymentUseCase: CompleteDeepCleaningPaymentUseCase,
    private val debitFromWalletDeepCleaningPaymentUseCase: DebitFromWalletDeepCleaningPaymentUseCase,
    private val dataStore: AMobileDataStore
    ) : ViewModel() {

    private var _deepCleaningCheckoutState = MutableStateFlow<DeepCleaningCheckoutState>(DeepCleaningCheckoutState.Idle)
    val deepCleaningCheckoutState = _deepCleaningCheckoutState.asStateFlow()

    fun initPayment(regionId: Int, locationId: Int, apartmentId: Int, cleaningTypeId: Int, numberOfRoomsId: Int, isPostConstruction: Boolean,cleaningDate:String,cleaningTime:String, address: String,images:List<String>) {
        viewModelScope.launch {
            val customerId = dataStore.getUserId().first()
            val request = InitDeepCleaningPaymentRequest(customerId.toInt(),regionId,locationId,apartmentId,cleaningTypeId,numberOfRoomsId,isPostConstruction,cleaningDate,cleaningTime,address,images)

            _deepCleaningCheckoutState.value = DeepCleaningCheckoutState.Loading
            initDeepCleaningPaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _deepCleaningCheckoutState.value = DeepCleaningCheckoutState.InitPaymentSuccess(response.data)
                    is UseCaseResult.Error -> _deepCleaningCheckoutState.value = DeepCleaningCheckoutState.Error(response.message)
                }
            }
        }
    }

    fun completePayment(ref: String, txnRef: String) {
        viewModelScope.launch {
            _deepCleaningCheckoutState.value = DeepCleaningCheckoutState.Loading
            val customerId = dataStore.getUserId().first()
          val   request= CompleteDeepCleaningPaymentRequest(customerId.toInt(),ref,txnRef)
            completeDeepCleaningPaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _deepCleaningCheckoutState.value = DeepCleaningCheckoutState.CompletePaymentSuccess(response.data)
                    is UseCaseResult.Error -> _deepCleaningCheckoutState.value = DeepCleaningCheckoutState.Error(response.message)
                }
            }
        }
    }

    fun debitFromWallet(regionId: Int, locationId: Int, apartmentId: Int, cleaningTypeId: Int, numberOfRoomsId: Int, isPostConstruction: Boolean,cleaningDate:String,cleaningTime:String, address: String,images:List<String>) {

        viewModelScope.launch {
            val customerId = dataStore.getUserId().first()
            val  request= DebitFromWalletDeepCleaningPaymentRequest(customerId.toInt(),regionId,locationId,apartmentId,cleaningTypeId,numberOfRoomsId,isPostConstruction,cleaningDate,cleaningTime,address,images)
            _deepCleaningCheckoutState.value = DeepCleaningCheckoutState.Loading
            debitFromWalletDeepCleaningPaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _deepCleaningCheckoutState.value =
                        DeepCleaningCheckoutState.PaymentSuccess(response.data)

                    is UseCaseResult.Error -> _deepCleaningCheckoutState.value =
                        DeepCleaningCheckoutState.Error(response.message)
                }
            }
        }
    }

    fun clearError(){
        _deepCleaningCheckoutState.value = DeepCleaningCheckoutState.Idle
    }
}
