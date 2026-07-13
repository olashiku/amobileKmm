package com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.basic_cleaning_checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.CompleteBasicCleaningPaymentRequest
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.DebitFromWalletBasicCleaningPaymentRequest
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.InitBasicCleaningPaymentRequest
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.CompleteBasicCleaningPaymentUseCase
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.DebitFromWalletBasicCleaningPaymentUseCase
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.InitBasicCleaningPaymentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BasicCleaningCheckoutViewModel(
    private val initBasicCleaningPaymentUseCase: InitBasicCleaningPaymentUseCase,
    private val debitFromWalletBasicCleaningPaymentUseCase: DebitFromWalletBasicCleaningPaymentUseCase,
    private val completeBasicCleaningPaymentUseCase: CompleteBasicCleaningPaymentUseCase,
    private val dataStore: AMobileDataStore
) : ViewModel() {

    private val _basicCleaningState = MutableStateFlow<BasicCleaningState>(BasicCleaningState.Idle)
    val basicCleaningState = _basicCleaningState.asStateFlow()

    private val paymentRef = MutableStateFlow("")

    fun saveReference(reference: String) {
        paymentRef.value = reference
    }

    fun initBasicCleaningPayment(
        reference: String,
        apartmentTypeId: Int,
        images: List<String>,
        regionId: Int,
        locationId: Int,
        address: String,
    ) {
        viewModelScope.launch {
            val customerId = dataStore.getUserId().first()
            val request = InitBasicCleaningPaymentRequest(
                reference = reference,
                apartmentTypeId = apartmentTypeId,
                images = images,
                regionId = regionId,
                locationId = locationId,
                address = address,
                customerId = customerId.toInt()
            )
            _basicCleaningState.value = BasicCleaningState.Loading
            initBasicCleaningPaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> {
                        _basicCleaningState.value =
                            BasicCleaningState.InitBasicCleaningSuccess(response.data)
                    }

                    is UseCaseResult.Error -> {
                        _basicCleaningState.value = BasicCleaningState.Error(response.message)
                    }
                }
            }
        }
    }


    fun debitFromWalletBasicCleaningPayment(
        reference: String,
        apartmentTypeId: Int,
        images: List<String>,
        regionId: Int,
        locationId: Int,
        address: String,
    ) {
        viewModelScope.launch {
            val customerId = dataStore.getUserId().first()
            _basicCleaningState.value = BasicCleaningState.Loading
            val request = DebitFromWalletBasicCleaningPaymentRequest(
                reference = reference,
                apartmentTypeId = apartmentTypeId,
                images = images,
                regionId = regionId,
                locationId = locationId,
                address = address,
                customerId = customerId.toInt()
            )
            debitFromWalletBasicCleaningPaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> {
                        _basicCleaningState.value =
                            BasicCleaningState.CompleteBasicCleaningSuccess(response.data)
                    }

                    is UseCaseResult.Error -> {
                        _basicCleaningState.value = BasicCleaningState.Error(response.message)
                    }
                }
            }
        }
    }

    fun completeBasicCleaningPayment(
        txnRef: String
    ) {
        viewModelScope.launch {
            val customerId = dataStore.getUserId().first()

            _basicCleaningState.value = BasicCleaningState.Loading
            val request = CompleteBasicCleaningPaymentRequest(
                customerId = customerId.toInt(),
                ref = paymentRef.value,
                txnRef = txnRef
            )
            completeBasicCleaningPaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> {
                        _basicCleaningState.value =
                            BasicCleaningState.CompleteBasicCleaningSuccess(response.data)
                    }

                    is UseCaseResult.Error -> {
                        _basicCleaningState.value = BasicCleaningState.Error(response.message)
                    }
                }
            }
        }
    }

    fun resetState() {
        _basicCleaningState.value = BasicCleaningState.Idle
    }


}