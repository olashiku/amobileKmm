package com.exquisite.a_mobile_kmm.feature.address.presenter.address_form

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun AddressFormScreen(viewModel: AddressFormViewModel) {
    val state by viewModel.addressFormState.collectAsState()

    when (state) {
        is AddressFormState.Idle -> {
            // Initial state
        }
        is AddressFormState.Loading -> {
            // Show loading indicator
        }
        is AddressFormState.CreateAddressSuccess -> {
            // Show create success
        }
        is AddressFormState.UpdateAddressSuccess -> {
            // Show update success
        }
        is AddressFormState.Error -> {
            // Show error message
        }
    }
}
