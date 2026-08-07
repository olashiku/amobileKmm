package com.exquisite.a_mobile_kmm.feature.profile_and_settings.presenter.profile_form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.profile_and_settings.domain.model.ChangePasswordRequest
import com.exquisite.a_mobile_kmm.feature.profile_and_settings.domain.model.EditProfileRequest
import com.exquisite.a_mobile_kmm.feature.profile_and_settings.domain.usecase.ChangePasswordUseCase
import com.exquisite.a_mobile_kmm.feature.profile_and_settings.domain.usecase.EditProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class UserData(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phone: String = "",
    val customerId: Int = 0,
    val profilePicture: String = ""
)

class ProfileFormViewModel(
    private val editProfileUseCase: EditProfileUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val dataStore: AMobileDataStore
) : ViewModel() {

    private var _profileFormState = MutableStateFlow<ProfileFormState>(ProfileFormState.Idle)
    val profileFormState = _profileFormState.asStateFlow()

    private var _userData = MutableStateFlow(UserData())
    val userData = _userData.asStateFlow()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            val fullName = dataStore.getCustomerName().first()
            val nameParts = fullName.split(" ")
            val firstName = nameParts.firstOrNull() ?: ""
            val lastName = nameParts.drop(1).joinToString(" ")
            val email = dataStore.getUserEmail().first()
            val customerId = dataStore.getUserId().first().toInt()
            val profilePicture = dataStore.getProfilePicture().first()

            _userData.value = UserData(
                firstName = firstName,
                lastName = lastName,
                email = email,
                phone = "",
                customerId = customerId,
                profilePicture = profilePicture
            )
        }
    }

    fun editProfile(request: EditProfileRequest) {
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

    fun changePassword(request: ChangePasswordRequest) {
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

    fun resetState() {
        _profileFormState.value = ProfileFormState.Idle
    }
}
