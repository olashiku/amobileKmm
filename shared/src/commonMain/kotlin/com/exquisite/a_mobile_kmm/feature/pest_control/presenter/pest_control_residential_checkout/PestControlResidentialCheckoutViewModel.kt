package com.exquisite.a_mobile_kmm.feature.pest_control.presenter.pest_control_residential_checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.CompletePestControlPaymentRequest
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.DebitFromWalletPestControlRequest
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.InitPestControlPaymentRequest
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.usecase.CompletePestControlPaymentUseCase
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.usecase.DebitFromWalletPestControlUseCase
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.usecase.InitPestControlPaymentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PestControlResidentialCheckoutViewModel(
    private val debitFromWalletPestControlUseCase: DebitFromWalletPestControlUseCase,
    private val initPestControlPaymentUseCase: InitPestControlPaymentUseCase,
    private val completePestControlPaymentUseCase: CompletePestControlPaymentUseCase,
    private val dataStore: AMobileDataStore
) : ViewModel() {

    private var _pestControlResidentialCheckoutState = MutableStateFlow<PestControlResidentialCheckoutState>(PestControlResidentialCheckoutState.Idle)
    val pestControlResidentialCheckoutState = _pestControlResidentialCheckoutState.asStateFlow()

    private val paymentRef = MutableStateFlow("")
    val paymentRefState = paymentRef.asStateFlow()

    fun debitFromWallet(
        uniqueRef:String,
        address: String,
        images: List<String>,
        apartmentTypeId: Int,
        isHotFogging: Boolean,
        serviceDate: String,
        inspectionDate: String,
        serviceTime: String,
        inspectionTime: String,
        extraNote: String,
        customerOwnVehicle: Boolean,
        numberOfVehicles: Int
    ) {
        viewModelScope.launch {
            val customerId = dataStore.getUserId().first()
            val request = DebitFromWalletPestControlRequest(
                uniqueRef = uniqueRef,
                customerId = customerId.toInt(),
                address = address,
                images = images,
                apartmentTypeId = apartmentTypeId,
                isHotFogging = isHotFogging,
                serviceDate = serviceDate,
                inspectionDate = inspectionDate,
                serviceTime = serviceTime,
                inspectionTime = inspectionTime,
                extraNote = extraNote,
                customerOwnVehicle = customerOwnVehicle,
                numberOfVehicles = numberOfVehicles
            )
            _pestControlResidentialCheckoutState.value = PestControlResidentialCheckoutState.Loading
            debitFromWalletPestControlUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _pestControlResidentialCheckoutState.value = PestControlResidentialCheckoutState.DebitWalletSuccess(response.data)
                    is UseCaseResult.Error -> _pestControlResidentialCheckoutState.value = PestControlResidentialCheckoutState.Error(response.message)
                }
            }
        }
    }

    fun initPayment(
        uniqueRef:String,
        address: String,
        images: List<String>,
        apartmentTypeId: Int,
        isHotFogging: Boolean,
        serviceDate: String,
        inspectionDate: String,
        serviceTime: String,
        inspectionTime: String,
        extraNote: String,
        customerOwnVehicle: Boolean,
        numberOfVehicles: Int
    ) {
        viewModelScope.launch {
            val customerId = dataStore.getUserId().first()
            val request = InitPestControlPaymentRequest(
                uniqueRef = uniqueRef,
                customerId = customerId.toInt(),
                address = address,
                images = images,
                apartmentTypeId = apartmentTypeId,
                isHotFogging = isHotFogging,
                serviceDate = serviceDate,
                inspectionDate = inspectionDate,
                serviceTime = serviceTime,
                inspectionTime = inspectionTime,
                extraNote = extraNote,
                customerOwnVehicle = customerOwnVehicle,
                numberOfVehicles = numberOfVehicles
            )
            _pestControlResidentialCheckoutState.value = PestControlResidentialCheckoutState.Loading
            initPestControlPaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _pestControlResidentialCheckoutState.value = PestControlResidentialCheckoutState.InitPaymentSuccess(response.data)
                    is UseCaseResult.Error -> _pestControlResidentialCheckoutState.value = PestControlResidentialCheckoutState.Error(response.message)
                }
            }
        }
    }

    fun completePayment(txnRef: String) {
        viewModelScope.launch {
            val customerId = dataStore.getUserId().first()
            val request = CompletePestControlPaymentRequest(
                customerId = customerId.toInt(),
                ref = paymentRef.value,
                txnRef = txnRef
            )
            _pestControlResidentialCheckoutState.value = PestControlResidentialCheckoutState.Loading
            completePestControlPaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _pestControlResidentialCheckoutState.value = PestControlResidentialCheckoutState.CompletePaymentSuccess(response.data)
                    is UseCaseResult.Error -> _pestControlResidentialCheckoutState.value = PestControlResidentialCheckoutState.Error(response.message)
                }
            }
        }
    }

    fun clearError(){
        _pestControlResidentialCheckoutState.value = PestControlResidentialCheckoutState.Idle
    }

    fun saveReference(reference: String) {
        paymentRef.value = reference
    }
}
