package com.exquisite.a_mobile_kmm.feature.janitorial.presenter.janitorial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.auth.domain.usecase.UploadFileUseCase
import com.exquisite.a_mobile_kmm.feature.auth.presenter.upload_image.ImageUploadState
import com.exquisite.a_mobile_kmm.feature.janitorial.domain.model.CreateJanitorialRequestModel
import com.exquisite.a_mobile_kmm.feature.janitorial.domain.usecase.CreateJanitorialUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class JanitorialViewModel(
    private val createJanitorialUseCase: CreateJanitorialUseCase,
    private val uploadFileUseCase: UploadFileUseCase,
    private val dataStore: AMobileDataStore
) : ViewModel() {

    private var _janitorialState = MutableStateFlow<JanitorialState>(JanitorialState.Idle)
    val janitorialState = _janitorialState.asStateFlow()

    private val _imageUploadState = MutableStateFlow<ImageUploadState>(ImageUploadState.Idle)
    val imageUploadState = _imageUploadState.asStateFlow()


    fun createJanitorial(
         companyName: String,
         companyEmail: String,
         companyAddress: String,
         availabilityDate: String,
         availabilityTime: String,
         resumptionTime: String,
         buildingImage: List<String>,
         phoneNo:String
    ) {
        viewModelScope.launch {
            val customerId = dataStore.getUserId().first()
            val request = CreateJanitorialRequestModel(customerId.toInt(),companyName,
                companyEmail, companyAddress,availabilityDate,availabilityTime,
                resumptionTime,buildingImage,phoneNo)
            _janitorialState.value = JanitorialState.Loading
            createJanitorialUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _janitorialState.value = JanitorialState.Success(response.data)
                    is UseCaseResult.Error -> _janitorialState.value = JanitorialState.Error(response.message)
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
                        else -> {}
                    }
                }
        }
    }


}
