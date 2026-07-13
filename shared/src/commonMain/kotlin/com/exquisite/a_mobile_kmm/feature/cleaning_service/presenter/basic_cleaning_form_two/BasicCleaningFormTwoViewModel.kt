package com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.basic_cleaning_form_two

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.auth.domain.usecase.UploadFileUseCase
import com.exquisite.a_mobile_kmm.feature.auth.presenter.upload_image.ImageUploadState
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.BasicCleaningForm2Model
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.DeepCleaningFormData
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.FindAllRegionsUseCase
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.FindApartmentTypeUseCase
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.FindLocationByRegionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BasicCleaningFormTwoViewModel(
    private val findLocationByRegionUseCase: FindLocationByRegionUseCase,
    private val findAllRegionsUseCase: FindAllRegionsUseCase,
    private val findApartmentTypeUseCase: FindApartmentTypeUseCase,
    private val uploadFileUseCase: UploadFileUseCase
) : ViewModel() {

    private val _imageUploadState = MutableStateFlow<ImageUploadState>(ImageUploadState.Idle)
    val imageUploadState = _imageUploadState.asStateFlow()


    private val _isRegionLoading = MutableStateFlow(false)
    val isRegionLoading = _isRegionLoading.asStateFlow()

    private val _isLocationLoading = MutableStateFlow(false)
    val isLocationLoading = _isLocationLoading.asStateFlow()

    private val _isApartmentTypeLoading = MutableStateFlow(false)
    val isApartmentTypeLoading = _isApartmentTypeLoading.asStateFlow()

    private val _regions = MutableStateFlow<List<com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.RegionModel>>(emptyList())
    val regions = _regions.asStateFlow()

    private val _locations = MutableStateFlow<List<com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.LocationModel>>(emptyList())
    val locations = _locations.asStateFlow()

    private val _apartmentTypes = MutableStateFlow<List<com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.ApartmentTypeModel>>(emptyList())
    val apartmentTypes = _apartmentTypes.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _persistedFormData = MutableStateFlow(BasicCleaningForm2Model())
    val persistedFormData = _persistedFormData.asStateFlow()

    private val _uploadedImageUrls = MutableStateFlow<List<String>>(emptyList())
    val uploadedImageUrls = _uploadedImageUrls.asStateFlow()

    fun uploadImage(image: ByteArray, fileName: String) {
        viewModelScope.launch {
            _imageUploadState.value = ImageUploadState.Loading
            uploadFileUseCase.invoke(image, fileName)
                .collect { result ->
                    when (result) {
                        is UseCaseResult.Success -> {
                            _imageUploadState.value = ImageUploadState.Success(result.data)
                        }
                        is UseCaseResult.Error -> {
                            _imageUploadState.value = ImageUploadState.Error(result.message)
                        }
                    }
                }
        }
    }


    fun callAllEndpoints(){
        findApartmentTypes()
        findAllRegions()
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

    fun saveFormData(basicCleaningForm2Model:BasicCleaningForm2Model

    ) {
        _persistedFormData.value = basicCleaningForm2Model
    }

    fun clearError() {
        _errorMessage.value = null
    }

    fun removeImageUrl(url: String) {
        _uploadedImageUrls.value = _uploadedImageUrls.value.filter { it != url }
    }

    fun addImageUrl(url: String) {
        if (!_uploadedImageUrls.value.contains(url)) {
            _uploadedImageUrls.value = _uploadedImageUrls.value + url
        }
    }
    fun clearImageUploadState() {
        _imageUploadState.value = ImageUploadState.Idle
    }
}