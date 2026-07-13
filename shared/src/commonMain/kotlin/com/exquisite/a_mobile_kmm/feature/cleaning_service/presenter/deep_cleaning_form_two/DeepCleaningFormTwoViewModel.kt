package com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.deep_cleaning_form_two

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.screen_components.DateModel
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.auth.domain.usecase.UploadFileUseCase
import com.exquisite.a_mobile_kmm.feature.auth.presenter.upload_image.ImageUploadState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DeepCleaningFormTwoViewModel(
    private val uploadFileUseCase: UploadFileUseCase,
    ): ViewModel() {


    private val _imageUploadState = MutableStateFlow<ImageUploadState>(ImageUploadState.Idle)
    val imageUploadState = _imageUploadState.asStateFlow()

    private val _selectedDate = MutableStateFlow<DateModel?>(null)
    val selectedDate = _selectedDate.asStateFlow()

    private val _selectedTime = MutableStateFlow<String?>(null)
    val selectedTime = _selectedTime.asStateFlow()

    private val _isPostConstruction = MutableStateFlow(false)
    val isPostConstruction = _isPostConstruction.asStateFlow()

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

    fun clearImageUploadState() {
        _imageUploadState.value = ImageUploadState.Idle
    }

    fun setSelectedDate(date: DateModel?) {
        _selectedDate.value = date
    }

    fun setSelectedTime(time: String?) {
        _selectedTime.value = time
    }

    fun setPostConstruction(isPostConstruction: Boolean) {
        _isPostConstruction.value = isPostConstruction
    }

    fun addImageUrl(url: String) {
        if (!_uploadedImageUrls.value.contains(url)) {
            _uploadedImageUrls.value = _uploadedImageUrls.value + url
        }
    }

    fun removeImageUrl(url: String) {
        _uploadedImageUrls.value = _uploadedImageUrls.value.filter { it != url }
    }

    fun clearFormData() {
        _selectedDate.value = null
        _selectedTime.value = null
        _isPostConstruction.value = false
        _uploadedImageUrls.value = emptyList()
    }
}