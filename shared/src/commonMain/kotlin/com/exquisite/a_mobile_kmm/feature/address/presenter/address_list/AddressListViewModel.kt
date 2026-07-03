package com.exquisite.a_mobile_kmm.feature.address.presenter.address_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.address.data.remote.request.DeleteAddressRequestDto
import com.exquisite.a_mobile_kmm.feature.address.domain.usecase.DeleteAddressUseCase
import com.exquisite.a_mobile_kmm.feature.address.domain.usecase.GetAddressesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class AddressListViewModel(
    private val getAddressesUseCase: GetAddressesUseCase,
    private val deleteAddressUseCase: DeleteAddressUseCase,
    private val aMobileDataStore: AMobileDataStore
) : ViewModel() {

    private var _addressListState = MutableStateFlow<AddressListState>(AddressListState.Idle)
    val addressListState = _addressListState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAddresses() {
        viewModelScope.launch {
            aMobileDataStore.getUserId()
                .flatMapLatest { customerId ->
                    _addressListState.value = AddressListState.Loading
                    getAddressesUseCase.invoke(customerId.toInt())
                }
                .collect { response ->
                    when (response) {
                        is UseCaseResult.Success -> _addressListState.value =
                            AddressListState.GetAddressesSuccess(response.data)

                        is UseCaseResult.Error -> _addressListState.value =
                            AddressListState.Error(response.message)
                    }
                }
        }
    }

    fun deleteAddress(request: DeleteAddressRequestDto) {
        viewModelScope.launch {
            _addressListState.value = AddressListState.Loading
            deleteAddressUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _addressListState.value =
                        AddressListState.DeleteAddressSuccess(response.data)

                    is UseCaseResult.Error -> _addressListState.value =
                        AddressListState.Error(response.message)
                }
            }
        }
    }
}
