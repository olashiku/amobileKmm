package com.exquisite.a_mobile_kmm.feature.profile.presenter.profile_form

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun ProfileFormScreen(viewModel: ProfileFormViewModel) {
    val state by viewModel.profileFormState.collectAsState()

    when (state) {
        is ProfileFormState.Idle -> {
            // Initial state
        }
        is ProfileFormState.Loading -> {
            // Show loading indicator
        }
        is ProfileFormState.EditProfileSuccess -> {
            // Show edit profile success
        }
        is ProfileFormState.ChangePasswordSuccess -> {
            // Show change password success
        }
        is ProfileFormState.Error -> {
            // Show error message
        }
    }
}
