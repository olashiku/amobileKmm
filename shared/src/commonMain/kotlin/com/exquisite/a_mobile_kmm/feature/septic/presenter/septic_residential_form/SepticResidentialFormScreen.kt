package com.exquisite.a_mobile_kmm.feature.septic.presenter.septic_residential_form

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun SepticResidentialFormScreen(viewModel: SepticResidentialFormViewModel) {
    val state by viewModel.septicResidentialFormState.collectAsState()

    when (state) {
        is SepticResidentialFormState.Idle -> {
            // Initial state
        }
        is SepticResidentialFormState.Loading -> {
            // Show loading indicator
        }
        is SepticResidentialFormState.GetTruckSizeSuccess -> {
            // Show truck size list
        }
        is SepticResidentialFormState.Error -> {
            // Show error message
        }
    }
}
