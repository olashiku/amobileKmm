package com.exquisite.a_mobile_kmm.feature.septic.presenter.septic_commercial_form

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun SepticCommercialFormScreen(viewModel: SepticCommercialFormViewModel) {
    val state by viewModel.septicCommercialFormState.collectAsState()

    when (state) {
        is SepticCommercialFormState.Idle -> {
            // Initial state
        }
        is SepticCommercialFormState.Loading -> {
            // Show loading indicator
        }
        is SepticCommercialFormState.SendEnquirySuccess -> {
            // Show enquiry sent success
        }
        is SepticCommercialFormState.Error -> {
            // Show error message
        }
    }
}
