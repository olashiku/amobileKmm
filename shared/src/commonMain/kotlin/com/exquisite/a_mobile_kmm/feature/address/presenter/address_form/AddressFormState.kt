package com.exquisite.a_mobile_kmm.feature.address.presenter.address_form

import com.exquisite.a_mobile_kmm.feature.address.domain.model.AddressResponseModel

sealed class AddressFormState {
    data object Idle : AddressFormState()
    data object Loading : AddressFormState()
    data class CreateAddressSuccess(val data: AddressResponseModel) : AddressFormState()
    data class UpdateAddressSuccess(val data: AddressResponseModel) : AddressFormState()
    data class Error(val message: String) : AddressFormState()
}
