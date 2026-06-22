package com.exquisite.a_mobile_kmm.feature.address.presenter.address_list

import com.exquisite.a_mobile_kmm.feature.address.domain.model.AddressModel
import com.exquisite.a_mobile_kmm.feature.address.domain.model.AddressResponseModel

sealed class AddressListState {
    data object Idle : AddressListState()
    data object Loading : AddressListState()
    data class GetAddressesSuccess(val data: List<AddressModel>) : AddressListState()
    data class DeleteAddressSuccess(val data: AddressResponseModel) : AddressListState()
    data class Error(val message: String) : AddressListState()
}
