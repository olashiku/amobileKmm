package com.exquisite.a_mobile_kmm.feature.pest_control.presenter.pest_control_residential_form_two

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.screen_components.DateModel
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.auth.domain.usecase.UploadFileUseCase
import com.exquisite.a_mobile_kmm.feature.auth.presenter.upload_image.ImageUploadState
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.ApartmentTypeModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.FindApartmentTypeUseCase
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.ResidentialPestControlFormTwoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class ResidentialPestControlForm2ViewModel(
    private val uploadFileUseCase: UploadFileUseCase,
    private val findApartmentTypeUseCase: FindApartmentTypeUseCase
): ViewModel() {

    private val _formState = MutableStateFlow(ResidentialPestControlFormTwoModel())
    val formState = _formState.asStateFlow()

    private val _imageUploadState = MutableStateFlow<ImageUploadState>(ImageUploadState.Idle)
    val imageUploadState = _imageUploadState.asStateFlow()

    private val _isApartmentTypeLoading = MutableStateFlow(false)
    val isApartmentTypeLoading = _isApartmentTypeLoading.asStateFlow()


    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()


    private val _apartmentTypes = MutableStateFlow<List<ApartmentTypeModel>>(emptyList())
    val apartmentTypes = _apartmentTypes.asStateFlow()

     init{
         findApartmentTypes()
     }

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
                        else -> {}
                    }
                }
        }
    }

    fun addImageUrl(url: String) {
        if (!formState.value.images.contains(url)) {
            updateForm { it.copy(images = it.images + url) }
        }
        _imageUploadState.value = ImageUploadState.Idle
    }

    private fun updateForm(update: (ResidentialPestControlFormTwoModel) -> ResidentialPestControlFormTwoModel) {
        _formState.update(update)
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

    fun clearError() {
        _errorMessage.value = null
    }

    fun setAddress(address: String) = updateForm { it.copy(address = address) }

    fun setInspectionDate(date: DateModel?) = updateForm { it.copy(inspectionDate = date) }

    fun setInspectionTime(time: String?) = updateForm { it.copy(inspectionTime = time) }

    fun setServiceDate(date: DateModel?) = updateForm { it.copy(serviceDate = date) }

    fun setServiceTime(time: String?) = updateForm { it.copy(serviceTime = time) }

    fun setExtraNote(note: String) = updateForm { it.copy(extraNote = note) }

    fun setHasPestInVehicle(hasPest: Boolean) = updateForm { it.copy(hasPestInVehicle = hasPest) }

    fun setNumberOfVehicles(count: String) = updateForm { it.copy(numberOfVehicles = count) }

    fun setWantsHotFogging(wants: Boolean) = updateForm { it.copy(wantsHotFogging = wants) }

    fun setApartmentType(type: Pair<String, String>) = updateForm { it.copy(typeOfApartment = type) }


    fun removeImageUrl(url: String) = updateForm { it.copy(images = it.images.filter { img -> img != url }) }


}