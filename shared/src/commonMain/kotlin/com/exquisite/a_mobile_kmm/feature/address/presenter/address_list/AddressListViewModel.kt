package com.exquisite.a_mobile_kmm.feature.address.presenter.address_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.address.domain.model.AddressModel
import com.exquisite.a_mobile_kmm.feature.address.domain.usecase.DeleteAddressUseCase
import com.exquisite.a_mobile_kmm.feature.address.domain.usecase.GetAddressesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AddressListViewModel(
    private val getAddressesUseCase: GetAddressesUseCase,
    private val deleteAddressUseCase: DeleteAddressUseCase,
    private val aMobileDataStore: AMobileDataStore
) : ViewModel() {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

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
                        is UseCaseResult.Success ->
                            _addressListState.value =
                                AddressListState.GetAddressesSuccess(response.data)

                        is UseCaseResult.Error -> _addressListState.value =
                            AddressListState.Error(response.message)
                    }
                }
        }
    }

    fun selectAddress(selectedAddressId: Int) {
        _addressListState.update { state ->
            if (state is AddressListState.GetAddressesSuccess) {
                state.copy(data = state.data.map { it.copy(isSelected = it.id == selectedAddressId) })
            } else state
        }
    }

    fun saveSelectedAddress(address: AddressModel) {
        viewModelScope.launch {
            try {
                val addressJson = json.encodeToString<AddressModel>(address)
                aMobileDataStore.saveSelectedAddress(addressJson)
            } catch (e: Exception) {
                _addressListState.value = AddressListState.Error(e.message ?: "Failed to save address")
            }
        }
    }

    fun saveSelectedAddressToDataStore(address: AddressModel) {
        viewModelScope.launch {
            try {
                val addressJson = json.encodeToString(address)
                aMobileDataStore.saveSelectedAddress(addressJson)
            } catch (e: Exception) {
                _addressListState.value = AddressListState.Error(e.message ?: "Failed to save address")
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun deleteAddress(addressId: Int) {
        viewModelScope.launch {
            aMobileDataStore.getUserId()
                .flatMapLatest { customerId ->
                    _addressListState.value = AddressListState.Loading
                    deleteAddressUseCase.invoke(addressId, customerId.toInt())
                }.collect { response ->
                    when (response) {
                        is UseCaseResult.Success -> {
                            getAddresses()
                        }
                        is UseCaseResult.Error -> _addressListState.value =
                            AddressListState.Error(response.message)
                    }
                }
        }

    }
}
