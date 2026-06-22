package com.exquisite.a_mobile_kmm.feature.address.presenter.address_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun AddressListScreen(viewModel: AddressListViewModel) {
    val state by viewModel.addressListState.collectAsState()

    when (state) {
        is AddressListState.Idle -> {
            // Initial state
        }
        is AddressListState.Loading -> {
            // Show loading indicator
        }
        is AddressListState.GetAddressesSuccess -> {
            // Show address list
        }
        is AddressListState.DeleteAddressSuccess -> {
            // Show delete success
        }
        is AddressListState.Error -> {
            // Show error message
        }
    }
}
