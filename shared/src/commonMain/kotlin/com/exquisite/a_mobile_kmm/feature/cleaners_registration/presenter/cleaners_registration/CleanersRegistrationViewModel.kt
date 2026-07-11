package com.exquisite.a_mobile_kmm.feature.cleaners_registration.presenter.cleaners_registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.auth.domain.usecase.UploadFileUseCase
import com.exquisite.a_mobile_kmm.feature.auth.presenter.upload_image.ImageUploadState
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.domain.model.PersistedFormData
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.domain.model.RegisterCleanerRequest
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.domain.usecase.RegisterCleanerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CleanersRegistrationViewModel(
    private val registerCleanerUseCase: RegisterCleanerUseCase,
    private val uploadFileUseCase: UploadFileUseCase,
    private val dataStore: AMobileDataStore
) : ViewModel() {

    private var _cleanersRegistrationState = MutableStateFlow<CleanersRegistrationState>(CleanersRegistrationState.Idle)
    val cleanersRegistrationState = _cleanersRegistrationState.asStateFlow()

    private val _imageUploadState = MutableStateFlow<ImageUploadState>(ImageUploadState.Idle)
    val imageUploadState = _imageUploadState.asStateFlow()

    // Persisted form values
    private val _persistedFormData = MutableStateFlow(PersistedFormData())
    val persistedFormData = _persistedFormData.asStateFlow()

    fun registerCleaner(
        fullName: String,
        email: String,
        phone: String,
        address: String,
        resumeUrl: String,
        pictureUrl: List<String>,
        gender: String,
        employmentStatus: String,
        experienceLevel:String
    ) {
        viewModelScope.launch {
            _cleanersRegistrationState.value = CleanersRegistrationState.Loading

            val customerId = dataStore.getUserId().first().toIntOrNull() ?: 0

            val request = RegisterCleanerRequest(
                customerId = customerId,
                fullName = fullName,
                email = email,
                phone = phone,
                address = address,
                resumeUrl = resumeUrl,
                pictureUrl = pictureUrl,
                gender = gender,
                employmentStatus = employmentStatus,
                experienceLevel = experienceLevel
            )

            registerCleanerUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _cleanersRegistrationState.value = CleanersRegistrationState.Success(response.data)
                    is UseCaseResult.Error ->
                        _cleanersRegistrationState.value = CleanersRegistrationState.Error(response.message)
                }
            }
        }
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
                    }
                }
        }
    }

    fun clearState() {
        _cleanersRegistrationState.value = CleanersRegistrationState.Idle
        _imageUploadState. value  = ImageUploadState.Idle
    }

     fun clearImageState() {
         _imageUploadState. value  = ImageUploadState.Idle

     }

    fun saveFormData(
        fullName: String,
        email: String,
        phone: String,
        address: String,
        gender: String,
        employmentStatus: String,
        experienceLevel: String
    ) {
        _persistedFormData.value = PersistedFormData(
            fullName = fullName,
            email = email,
            phone = phone,
            address = address,
            gender = gender,
            employmentStatus = employmentStatus,
            experienceLevel = experienceLevel
        )
    }

    fun clearFormData() {
        _persistedFormData.value = PersistedFormData()
    }
}


