package com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.mobile_toilet_event_form_three

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.nav.NavigationUtils
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.auth.domain.usecase.UploadFileUseCase
import com.exquisite.a_mobile_kmm.feature.auth.presenter.upload_image.ImageUploadState
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.MobileToiletFormThreeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MobileToiletFormThreeViewModel(private val uploadFileUseCase: UploadFileUseCase) : ViewModel() {

    private val _imageUploadState = MutableStateFlow<ImageUploadState>(ImageUploadState.Idle)
    val imageUploadState = _imageUploadState.asStateFlow()

    private val _formModel = MutableStateFlow(MobileToiletFormThreeModel())
    val formModel = _formModel.asStateFlow()

    private var currentImageType: String = ""

    fun setCompanyName(companyName: String) {
        _formModel.value = _formModel.value.copy(companyName = companyName)
    }

    fun setCompanyEmail(companyEmail: String) {
        _formModel.value = _formModel.value.copy(companyEmail = companyEmail)
    }

    fun setContactName(contactName: String) {
        _formModel.value = _formModel.value.copy(contactName = contactName)
    }

    fun setContactPhone(contactPhone: String) {
        _formModel.value = _formModel.value.copy(contactPhone = contactPhone)
    }

    fun setContactEmail(contactEmail: String) {
        _formModel.value = _formModel.value.copy(contactEmail = contactEmail)
    }

    fun setEventType(eventType: String) {
        _formModel.value = _formModel.value.copy(eventType = eventType)
    }

    fun setAddress(address: String) {
        _formModel.value = _formModel.value.copy(address = address)
    }

    fun setAdditionalMessage(additionalMessage: String) {
        _formModel.value = _formModel.value.copy(additionalMessage = additionalMessage)
    }

    fun addEventLocationImage(imageUrl: String) {
        val currentImages = _formModel.value.eventLocationImages.toMutableList()
        if (currentImages.size < 5) {
            currentImages.add(imageUrl)
            _formModel.value = _formModel.value.copy(eventLocationImages = currentImages)
        }
    }

    fun removeEventLocationImage(imageUrl: String) {
        val currentImages = _formModel.value.eventLocationImages.toMutableList()
        currentImages.remove(imageUrl)
        _formModel.value = _formModel.value.copy(eventLocationImages = currentImages)
    }

    fun addToiletPlacementImage(imageUrl: String) {
        val currentImages = _formModel.value.toiletPlacementImages.toMutableList()
        if (currentImages.size < 5) {
            currentImages.add(imageUrl)
            _formModel.value = _formModel.value.copy(toiletPlacementImages = currentImages)
        }
    }

    fun removeToiletPlacementImage(imageUrl: String) {
        val currentImages = _formModel.value.toiletPlacementImages.toMutableList()
        currentImages.remove(imageUrl)
        _formModel.value = _formModel.value.copy(toiletPlacementImages = currentImages)
    }

    fun setCurrentImageType(imageType: String) {
        currentImageType = imageType
    }

    fun uploadImage(image: ByteArray, fileName: String) {
        viewModelScope.launch {
            _imageUploadState.value = ImageUploadState.Loading
            uploadFileUseCase.invoke(image, fileName)
                .collect { result ->
                    when (result) {
                        is UseCaseResult.Success -> {
                            // Add image to the correct list based on imageType
                            when (currentImageType) {
                                "event_location" -> addEventLocationImage(result.data)
                                "toilet_placement" -> addToiletPlacementImage(result.data)
                            }
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

    fun getFormDataAsJson(): String {
        return NavigationUtils.encodeObject(_formModel.value)
    }

    fun clearImageUploadState() {
        _imageUploadState.value = ImageUploadState.Idle
    }
}