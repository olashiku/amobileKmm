package com.exquisite.a_mobile_kmm.feature.settings_and_profile.presenter.profile_form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.settings_and_profile.data.remote.request.ChangePasswordRequestDto
import com.exquisite.a_mobile_kmm.feature.settings_and_profile.data.remote.request.EditProfileRequestDto
import com.exquisite.a_mobile_kmm.feature.settings_and_profile.domain.usecase.ChangePasswordUseCase
import com.exquisite.a_mobile_kmm.feature.settings_and_profile.domain.usecase.EditProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileFormViewModel(
    private val editProfileUseCase: EditProfileUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase
) : ViewModel() {

    private var _profileFormState = MutableStateFlow<ProfileFormState>(ProfileFormState.Idle)
    val profileFormState = _profileFormState.asStateFlow()

    fun editProfile(request: EditProfileRequestDto) {
        viewModelScope.launch {
            _profileFormState.value = ProfileFormState.Loading
            editProfileUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _profileFormState.value = ProfileFormState.EditProfileSuccess(response.data)
                    is UseCaseResult.Error -> _profileFormState.value = ProfileFormState.Error(response.message)
                }
            }
        }
    }

    fun changePassword(request: ChangePasswordRequestDto) {
        viewModelScope.launch {
            _profileFormState.value = ProfileFormState.Loading
            changePasswordUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _profileFormState.value = ProfileFormState.ChangePasswordSuccess(response.data)
                    is UseCaseResult.Error -> _profileFormState.value = ProfileFormState.Error(response.message)
                }
            }
        }
    }
}
