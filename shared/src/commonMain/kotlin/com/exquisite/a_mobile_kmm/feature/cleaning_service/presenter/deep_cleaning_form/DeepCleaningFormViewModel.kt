package com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.deep_cleaning_form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.request.DebitFromWalletDeepCleaningPaymentRequestDto
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.request.GetCleaningPriceRequestDto
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.DeepCleaningFormData
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.DebitFromWalletDeepCleaningPaymentUseCase
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.FindAllRegionsUseCase
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.FindApartmentTypeUseCase
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.FindCleaningTypeUseCase
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.FindLocationByRegionUseCase
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.FindNumberOfRoomsUseCase
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.GetCleaningPriceUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DeepCleaningFormViewModel(
    private val findLocationByRegionUseCase: FindLocationByRegionUseCase,
    private val findAllRegionsUseCase: FindAllRegionsUseCase,
    private val findApartmentTypeUseCase: FindApartmentTypeUseCase,
    private val findNumberOfRoomsUseCase: FindNumberOfRoomsUseCase,
    private val findCleaningTypeUseCase: FindCleaningTypeUseCase,
    private val getCleaningPriceUseCase: GetCleaningPriceUseCase,
    private val debitFromWalletDeepCleaningPaymentUseCase: DebitFromWalletDeepCleaningPaymentUseCase,
    private val dataStore: AMobileDataStore
) : ViewModel() {

    private var _deepCleaningFormState = MutableStateFlow<DeepCleaningFormState>(DeepCleaningFormState.Idle)
    val deepCleaningFormState = _deepCleaningFormState.asStateFlow()

    private val _persistedFormData = MutableStateFlow(DeepCleaningFormData())
    val persistedFormData = _persistedFormData.asStateFlow()

    // Separate state flows for each data type to avoid race conditions
    private val _regions = MutableStateFlow<List<com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.RegionModel>>(emptyList())
    val regions = _regions.asStateFlow()

    private val _locations = MutableStateFlow<List<com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.LocationModel>>(emptyList())
    val locations = _locations.asStateFlow()

    private val _apartmentTypes = MutableStateFlow<List<com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.ApartmentTypeModel>>(emptyList())
    val apartmentTypes = _apartmentTypes.asStateFlow()

    private val _cleaningTypes = MutableStateFlow<List<com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.CleaningTypeModel>>(emptyList())
    val cleaningTypes = _cleaningTypes.asStateFlow()

    private val _numberOfRooms = MutableStateFlow<List<com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.NumberOfRoomsModel>>(emptyList())
    val numberOfRooms = _numberOfRooms.asStateFlow()

    private val _isRegionLoading = MutableStateFlow(false)
    val isRegionLoading = _isRegionLoading.asStateFlow()

    private val _isLocationLoading = MutableStateFlow(false)
    val isLocationLoading = _isLocationLoading.asStateFlow()

    private val _isApartmentTypeLoading = MutableStateFlow(false)
    val isApartmentTypeLoading = _isApartmentTypeLoading.asStateFlow()

    private val _isNumberOfRoomsLoading = MutableStateFlow(false)
    val isNumberOfRoomsLoading = _isNumberOfRoomsLoading.asStateFlow()

    private val _isCleaningTypeLoading = MutableStateFlow(false)
    val isCleaningTypeLoading = _isCleaningTypeLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()


    fun callAllEndpoints(){
        findApartmentTypes()
        findAllRegions()
        findCleaningTypes()
        findNumberOfRooms()
    }

    fun findAllRegions() {
        viewModelScope.launch {
            _isRegionLoading.value = true
            findAllRegionsUseCase.invoke().collect { response ->
                when (response) {
                    is UseCaseResult.Success -> {
                        _regions.value = response.data
                        _isRegionLoading.value = false
                    }

                    is UseCaseResult.Error -> {
                        _errorMessage.value = response.message
                        _isRegionLoading.value = false
                    }
                }
            }
        }
    }

    fun findLocationByRegion(regionId: Int) {
        viewModelScope.launch {
            _isLocationLoading.value = true
            findLocationByRegionUseCase.invoke(regionId).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> {
                        _locations.value = response.data
                        _isLocationLoading.value = false
                    }

                    is UseCaseResult.Error -> {
                        _errorMessage.value = response.message
                        _isLocationLoading.value = false
                    }
                }
            }
        }
    }

    fun findApartmentTypes() {
        viewModelScope.launch {
            _isApartmentTypeLoading.value = true
            findApartmentTypeUseCase.invoke().collect { response ->
                when (response) {
                    is UseCaseResult.Success -> {
                        _apartmentTypes.value = response.data
                        _isApartmentTypeLoading.value = false
                    }

                    is UseCaseResult.Error -> {
                        _errorMessage.value = response.message
                        _isApartmentTypeLoading.value = false
                    }
                }
            }
        }
    }

    fun findCleaningTypes() {
        viewModelScope.launch {
            _isCleaningTypeLoading.value = true
            findCleaningTypeUseCase.invoke().collect { response ->
                when (response) {
                    is UseCaseResult.Success -> {
                        _cleaningTypes.value = response.data
                        _isCleaningTypeLoading.value = false
                    }
                    is UseCaseResult.Error -> {
                        _errorMessage.value = response.message
                        _isCleaningTypeLoading.value = false
                    }
                }
            }
        }
    }

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

    fun getCleaningPrice(
        regionId: Int,
        locationId: Int,
        apartmentId: Int,
        cleaningTypeId: Int,
        numberOfRoomsId: Int
    ) {
        viewModelScope.launch {
            val customerId = dataStore.getUserId().first()

            val request = GetCleaningPriceRequestDto(
                customerId.toInt(),
                regionId,
                locationId,
                apartmentId,
                cleaningTypeId,
                numberOfRoomsId
            )
            _deepCleaningFormState.value = DeepCleaningFormState.Loading
            getCleaningPriceUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _deepCleaningFormState.value =
                        DeepCleaningFormState.PriceSuccess(response.data)

                    is UseCaseResult.Error -> _deepCleaningFormState.value =
                        DeepCleaningFormState.Error(response.message)
                }
            }
        }
    }

    fun debitFromWallet(request: DebitFromWalletDeepCleaningPaymentRequestDto) {
        viewModelScope.launch {
            _deepCleaningFormState.value = DeepCleaningFormState.Loading
            debitFromWalletDeepCleaningPaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _deepCleaningFormState.value =
                        DeepCleaningFormState.PaymentSuccess(response.data)

                    is UseCaseResult.Error -> _deepCleaningFormState.value =
                        DeepCleaningFormState.Error(response.message)
                }
            }
        }
    }

    fun saveFormData(
        region: Pair<String,String>,
        location:  Pair<String,String>,
        typeOfApartment:  Pair<String,String>,
        numberOfRooms:  Pair<String,String>,
        cleaningType:  Pair<String,String>,
        address:  Pair<String,String>
    ) {
        _persistedFormData.value = DeepCleaningFormData(
            region = region,
            location = location,
            typeOfApartment = typeOfApartment,
            numberOfRooms = numberOfRooms,
            cleaningType = cleaningType,
            address = address
        )
    }

    fun clearState() {
        _deepCleaningFormState.value = DeepCleaningFormState.Idle
    }

    fun clearError() {
        _errorMessage.value = null
    }

    fun clearFormData() {
        _persistedFormData.value = DeepCleaningFormData()
    }
}
