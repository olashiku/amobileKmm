package com.exquisite.a_mobile_kmm.feature.septic.presenter.septic_residential_checkout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun SepticResidentialCheckoutScreen(viewModel: SepticResidentialCheckoutViewModel) {
    val state by viewModel.septicResidentialCheckoutState.collectAsState()

    when (state) {
        is SepticResidentialCheckoutState.Idle -> {
            // Initial state
        }
        is SepticResidentialCheckoutState.Loading -> {
            // Show loading indicator
        }
        is SepticResidentialCheckoutState.InitPaymentSuccess -> {
            // Show payment initialization success
        }
        is SepticResidentialCheckoutState.DebitWalletSuccess -> {
            // Show wallet debit success
        }
        is SepticResidentialCheckoutState.CompletePaymentSuccess -> {
            // Show payment completion success
        }
        is SepticResidentialCheckoutState.Error -> {
            // Show error message
        }
    }
}
