package com.exquisite.a_mobile_kmm.feature.settings_and_profile.presenter.profile_form

import com.exquisite.a_mobile_kmm.feature.settings_and_profile.domain.model.ProfileResponseModel

sealed class ProfileFormState {
    data object Idle : ProfileFormState()
    data object Loading : ProfileFormState()
    data class EditProfileSuccess(val data: ProfileResponseModel) : ProfileFormState()
    data class ChangePasswordSuccess(val data: ProfileResponseModel) : ProfileFormState()
    data class Error(val message: String) : ProfileFormState()
}
