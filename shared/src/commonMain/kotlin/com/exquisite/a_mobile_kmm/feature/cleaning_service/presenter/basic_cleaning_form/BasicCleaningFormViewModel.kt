package com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.basic_cleaning_form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.*
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BasicCleaningFormViewModel(
    private val findNumberOfRoomsUseCase: FindNumberOfRoomsUseCase,
    private val getBasicCleaningBreakdownUseCase: GetBasicCleaningBreakdownUseCase,
    private val initBasicCleaningPaymentUseCase: InitBasicCleaningPaymentUseCase,
    private val debitFromWalletBasicCleaningPaymentUseCase: DebitFromWalletBasicCleaningPaymentUseCase,
    private val completeBasicCleaningPaymentUseCase: CompleteBasicCleaningPaymentUseCase,
    private val dataStore: AMobileDataStore
) : ViewModel() {

    private var _basicCleaningFormState = MutableStateFlow<BasicCleaningFormState>(BasicCleaningFormState.Idle)
    val basicCleaningFormState = _basicCleaningFormState.asStateFlow()

    private val _selectedTime = MutableStateFlow<String?>(null)
    val selectedTime = _selectedTime.asStateFlow()

    private val _numberOfRooms = MutableStateFlow<List<NumberOfRoomsModel>>(emptyList())
    val numberOfRooms = _numberOfRooms.asStateFlow()

    private val _persistedFormData = MutableStateFlow(BasicCleaningFormModel())
    val persistedFormData = _persistedFormData.asStateFlow()

    private val _isNumberOfRoomsLoading = MutableStateFlow(false)
    val isNumberOfRoomsLoading = _isNumberOfRoomsLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()


    fun findNumberOfRooms() {
        viewModelScope.launch {
            _isNumberOfRoomsLoading.value = true
            findNumberOfRoomsUseCase.invoke().collect { response ->
                when (response) {
                    is UseCaseResult.Success -> {
                        _numberOfRooms.value = response.data
                        _isNumberOfRoomsLoading.value = false
                    }

                    is UseCaseResult.Error -> {
                        _errorMessage.value = response.message
                        _isNumberOfRoomsLoading.value = false
                    }
                }
            }
        }
    }



    fun setSelectedTime(time: String?) {
        _selectedTime.value = time
    }

    fun getBasicCleaningBreakdown(
        numberOfRooms: Int,
        cleaningTime: String,
        cleaningDate: List<String>
    ) {
        viewModelScope.launch {
            val customerId = dataStore.getUserId().first()
            val request = GetBasicCleaningBreakdownRequest(
                numberOfRooms = numberOfRooms,
                cleaningTime = cleaningTime,
                customerId = customerId.toInt(),
                cleaningDate = cleaningDate
            )
            _basicCleaningFormState.value = BasicCleaningFormState.Loading
            getBasicCleaningBreakdownUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> {
                        _basicCleaningFormState.value = BasicCleaningFormState.Success(response.data)
                    }
                    is UseCaseResult.Error -> {
                        _basicCleaningFormState.value = BasicCleaningFormState.Error(response.message)
                    }
                }
            }
        }
    }

    fun saveFormData(basicCleaningFormModel:BasicCleaningFormModel){
        _persistedFormData.value = basicCleaningFormModel
    }

    // Init Basic Cleaning Payment
    private val _paymentModel = MutableStateFlow<PaymentModel?>(null)
    val paymentModel = _paymentModel.asStateFlow()

    private val _isPaymentLoading = MutableStateFlow(false)
    val isPaymentLoading = _isPaymentLoading.asStateFlow()

    fun initBasicCleaningPayment(
        reference: String,
        apartmentTypeId: Int,
        images: List<String>,
        regionId: Int,
        locationId: Int,
        address: String,
        customerId: Int
    ) {
        viewModelScope.launch {
            _isPaymentLoading.value = true
            val request = InitBasicCleaningPaymentRequest(
                reference = reference,
                apartmentTypeId = apartmentTypeId,
                images = images,
                regionId = regionId,
                locationId = locationId,
                address = address,
                customerId = customerId
            )
            initBasicCleaningPaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> {
                        _paymentModel.value = response.data
                        _isPaymentLoading.value = false
                    }
                    is UseCaseResult.Error -> {
                        _errorMessage.value = response.message
                        _isPaymentLoading.value = false
                    }
                }
            }
        }
    }

    // Debit from Wallet
    private val _walletPaymentResponse = MutableStateFlow<PaymentResponseModel?>(null)
    val walletPaymentResponse = _walletPaymentResponse.asStateFlow()

    private val _isWalletPaymentLoading = MutableStateFlow(false)
    val isWalletPaymentLoading = _isWalletPaymentLoading.asStateFlow()

    fun debitFromWalletBasicCleaningPayment(
        reference: String,
        apartmentTypeId: Int,
        images: List<String>,
        regionId: Int,
        locationId: Int,
        address: String,
        customerId: Int
    ) {
        viewModelScope.launch {
            _isWalletPaymentLoading.value = true
            val request = DebitFromWalletBasicCleaningPaymentRequest(
                reference = reference,
                apartmentTypeId = apartmentTypeId,
                images = images,
                regionId = regionId,
                locationId = locationId,
                address = address,
                customerId = customerId
            )
            debitFromWalletBasicCleaningPaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> {
                        _walletPaymentResponse.value = response.data
                        _isWalletPaymentLoading.value = false
                    }
                    is UseCaseResult.Error -> {
                        _errorMessage.value = response.message
                        _isWalletPaymentLoading.value = false
                    }
                }
            }
        }
    }

    // Complete Payment
    private val _completePaymentResponse = MutableStateFlow<PaymentResponseModel?>(null)
    val completePaymentResponse = _completePaymentResponse.asStateFlow()

    private val _isCompletePaymentLoading = MutableStateFlow(false)
    val isCompletePaymentLoading = _isCompletePaymentLoading.asStateFlow()

    fun completeBasicCleaningPayment(
        customerId: Int,
        ref: String,
        txnRef: String
    ) {
        viewModelScope.launch {
            _isCompletePaymentLoading.value = true
            val request = CompleteBasicCleaningPaymentRequest(
                customerId = customerId,
                ref = ref,
                txnRef = txnRef
            )
            completeBasicCleaningPaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> {
                        _completePaymentResponse.value = response.data
                        _isCompletePaymentLoading.value = false
                    }
                    is UseCaseResult.Error -> {
                        _errorMessage.value = response.message
                        _isCompletePaymentLoading.value = false
                    }
                }
            }
        }
    }

    fun resetState(){
        _basicCleaningFormState.value = BasicCleaningFormState.Idle
        _errorMessage.value = null
    }
}
