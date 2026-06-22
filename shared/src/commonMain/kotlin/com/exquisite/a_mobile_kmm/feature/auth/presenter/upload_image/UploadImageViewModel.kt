package com.exquisite.a_mobile_kmm.feature.auth.presenter.upload_image

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.auth.domain.usecase.CompleteRegisterUseCase
import com.exquisite.a_mobile_kmm.feature.auth.domain.usecase.UploadFileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UploadImageViewModel(
    private val uploadFileUseCase: UploadFileUseCase,
    private val completeRegisterUseCase: CompleteRegisterUseCase
) : ViewModel() {

    private val _completeProfileState =
        MutableStateFlow<CompleteProfileState>(CompleteProfileState.Idle)
    private val _imageUploadState = MutableStateFlow<ImageUploadState>(ImageUploadState.Idle)

    val completeProfileState = _completeProfileState.asStateFlow()
    val imageUploadState = _imageUploadState.asStateFlow()

    fun completeProfile(password: String, uniqueRef: String, otp: String, profilePicture: String) {
        viewModelScope.launch {
            _completeProfileState.value = CompleteProfileState.Loading
            completeRegisterUseCase.invoke(password, uniqueRef, otp, profilePicture)
                .collect { result ->
                    when (result) {
                        is UseCaseResult.Success -> {
                            _completeProfileState.value = CompleteProfileState.Success(result.data)
                        }
                        is UseCaseResult.Error -> {
                            _completeProfileState.value = CompleteProfileState.Error(result.message)
                        }
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
}