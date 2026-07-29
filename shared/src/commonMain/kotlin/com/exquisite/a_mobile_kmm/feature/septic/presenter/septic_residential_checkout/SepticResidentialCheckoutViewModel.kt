package com.exquisite.a_mobile_kmm.feature.septic.presenter.septic_residential_checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.septic.domain.model.CompleteSepticPaymentRequest
import com.exquisite.a_mobile_kmm.feature.septic.domain.model.DebitFromAccountSepticRequest
import com.exquisite.a_mobile_kmm.feature.septic.domain.model.InitSepticPaymentRequest
import com.exquisite.a_mobile_kmm.feature.septic.domain.usecase.CompleteSepticPaymentUseCase
import com.exquisite.a_mobile_kmm.feature.septic.domain.usecase.DebitFromAccountSepticUseCase
import com.exquisite.a_mobile_kmm.feature.septic.domain.usecase.InitSepticPaymentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SepticResidentialCheckoutViewModel(
    private val initSepticPaymentUseCase: InitSepticPaymentUseCase,
    private val debitFromAccountSepticUseCase: DebitFromAccountSepticUseCase,
    private val completeSepticPaymentUseCase: CompleteSepticPaymentUseCase,
    private val dataStore: AMobileDataStore
) : ViewModel() {

    private var _septicResidentialCheckoutState = MutableStateFlow<SepticResidentialCheckoutState>(SepticResidentialCheckoutState.Idle)
    val septicResidentialCheckoutState = _septicResidentialCheckoutState.asStateFlow()


    private val _reference = MutableStateFlow("")
    val reference = _reference.asStateFlow()

    fun initPayment(
         fullName: String,
         phoneNo: String,
         email: String,
         address: String,
         dateOfExcavation: String,
         timeOfExcavation: String,
         specialNote: String,
         septicSizeId: Int
    ) {
        viewModelScope.launch {
             val customerId = dataStore.getUserId().first()
            val request = InitSepticPaymentRequest(customerId.toInt(),fullName,phoneNo,email,address,dateOfExcavation,timeOfExcavation,specialNote,septicSizeId)
            _septicResidentialCheckoutState.value = SepticResidentialCheckoutState.Loading
            initSepticPaymentUseCase.invoke(request).collect { response ->

                when (response) {
                    is UseCaseResult.Success ->{
                        _reference.value = response.data.first
                        _septicResidentialCheckoutState.value = SepticResidentialCheckoutState.InitPaymentSuccess(response.data)}
                    is UseCaseResult.Error ->{ _septicResidentialCheckoutState.value = SepticResidentialCheckoutState.Error(response.message)}
                }
            }
        }
    }

    fun debitFromWallet(  fullName: String,
                          phoneNo: String,
                          email: String,
                          address: String,
                          dateOfExcavation: String,
                          timeOfExcavation: String,
                          specialNote: String,
                          septicSizeId: Int) {
        viewModelScope.launch {
            val customerId = dataStore.getUserId().first()
           val  request = DebitFromAccountSepticRequest(customerId.toInt(),fullName,phoneNo,email,address,dateOfExcavation,timeOfExcavation,specialNote,septicSizeId)
            _septicResidentialCheckoutState.value = SepticResidentialCheckoutState.Loading
            debitFromAccountSepticUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _septicResidentialCheckoutState.value = SepticResidentialCheckoutState.DebitWalletSuccess(response.data)
                    is UseCaseResult.Error -> _septicResidentialCheckoutState.value = SepticResidentialCheckoutState.Error(response.message)
                }
            }
        }
    }

    fun completePayment(txnRef: String) {
        viewModelScope.launch {
            val customerId = dataStore.getUserId().first()
            val request =  CompleteSepticPaymentRequest(customerId.toInt(),_reference.value,txnRef )
            _septicResidentialCheckoutState.value = SepticResidentialCheckoutState.Loading
            completeSepticPaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _septicResidentialCheckoutState.value = SepticResidentialCheckoutState.CompletePaymentSuccess(response.data)
                    is UseCaseResult.Error -> _septicResidentialCheckoutState.value = SepticResidentialCheckoutState.Error(response.message)
                }
            }
        }
    }

    fun clearError(){
        _septicResidentialCheckoutState.value = SepticResidentialCheckoutState.Idle
    }

    fun clearReference(){
        _reference.value = ""
    }
}
