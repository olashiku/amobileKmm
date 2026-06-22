package com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.deep_cleaning_form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.request.DebitFromWalletDeepCleaningPaymentRequestDto
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.request.GetCleaningPriceRequestDto
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DeepCleaningFormViewModel(
    private val findAllRegionsUseCase: FindAllRegionsUseCase,
    private val findLocationByRegionUseCase: FindLocationByRegionUseCase,
    private val findApartmentTypeUseCase: FindApartmentTypeUseCase,
    private val findCleaningTypeUseCase: FindCleaningTypeUseCase,
    private val findNumberOfRoomsUseCase: FindNumberOfRoomsUseCase,
    private val getCleaningPriceUseCase: GetCleaningPriceUseCase,
    private val debitFromWalletDeepCleaningPaymentUseCase: DebitFromWalletDeepCleaningPaymentUseCase
) : ViewModel() {

    private var _deepCleaningFormState = MutableStateFlow<DeepCleaningFormState>(DeepCleaningFormState.Idle)
    val deepCleaningFormState = _deepCleaningFormState.asStateFlow()

    fun findAllRegions() {
        viewModelScope.launch {
            _deepCleaningFormState.value = DeepCleaningFormState.Loading
            findAllRegionsUseCase.invoke().collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _deepCleaningFormState.value = DeepCleaningFormState.RegionsSuccess(response.data)
                    is UseCaseResult.Error -> _deepCleaningFormState.value = DeepCleaningFormState.Error(response.message)
                }
            }
        }
    }

    fun findLocationByRegion(regionId: Int) {
        viewModelScope.launch {
            _deepCleaningFormState.value = DeepCleaningFormState.Loading
            findLocationByRegionUseCase.invoke(regionId).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _deepCleaningFormState.value = DeepCleaningFormState.LocationsSuccess(response.data)
                    is UseCaseResult.Error -> _deepCleaningFormState.value = DeepCleaningFormState.Error(response.message)
                }
            }
        }
    }

    fun findApartmentTypes() {
        viewModelScope.launch {
            _deepCleaningFormState.value = DeepCleaningFormState.Loading
            findApartmentTypeUseCase.invoke().collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _deepCleaningFormState.value = DeepCleaningFormState.ApartmentTypesSuccess(response.data)
                    is UseCaseResult.Error -> _deepCleaningFormState.value = DeepCleaningFormState.Error(response.message)
                }
            }
        }
    }

    fun findCleaningTypes() {
        viewModelScope.launch {
            _deepCleaningFormState.value = DeepCleaningFormState.Loading
            findCleaningTypeUseCase.invoke().collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _deepCleaningFormState.value = DeepCleaningFormState.CleaningTypesSuccess(response.data)
                    is UseCaseResult.Error -> _deepCleaningFormState.value = DeepCleaningFormState.Error(response.message)
                }
            }
        }
    }

    fun findNumberOfRooms() {
        viewModelScope.launch {
            _deepCleaningFormState.value = DeepCleaningFormState.Loading
            findNumberOfRoomsUseCase.invoke().collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _deepCleaningFormState.value = DeepCleaningFormState.NumberOfRoomsSuccess(response.data)
                    is UseCaseResult.Error -> _deepCleaningFormState.value = DeepCleaningFormState.Error(response.message)
                }
            }
        }
    }

    fun getCleaningPrice(request: GetCleaningPriceRequestDto) {
        viewModelScope.launch {
            _deepCleaningFormState.value = DeepCleaningFormState.Loading
            getCleaningPriceUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _deepCleaningFormState.value = DeepCleaningFormState.PriceSuccess(response.data)
                    is UseCaseResult.Error -> _deepCleaningFormState.value = DeepCleaningFormState.Error(response.message)
                }
            }
        }
    }

    fun debitFromWallet(request: DebitFromWalletDeepCleaningPaymentRequestDto) {
        viewModelScope.launch {
            _deepCleaningFormState.value = DeepCleaningFormState.Loading
            debitFromWalletDeepCleaningPaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _deepCleaningFormState.value = DeepCleaningFormState.PaymentSuccess(response.data)
                    is UseCaseResult.Error -> _deepCleaningFormState.value = DeepCleaningFormState.Error(response.message)
                }
            }
        }
    }
}
