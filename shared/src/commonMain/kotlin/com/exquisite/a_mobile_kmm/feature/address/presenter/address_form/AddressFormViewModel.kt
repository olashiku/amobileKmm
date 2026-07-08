package com.exquisite.a_mobile_kmm.feature.address.presenter.address_form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.address.domain.usecase.CreateAddressUseCase
import com.exquisite.a_mobile_kmm.feature.address.domain.usecase.UpdateAddressUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AddressFormViewModel(
    private val createAddressUseCase: CreateAddressUseCase,
    private val updateAddressUseCase: UpdateAddressUseCase,
    private val dataStore: AMobileDataStore
) : ViewModel() {

    private var _addressFormState = MutableStateFlow<AddressFormState>(AddressFormState.Idle)
    val addressFormState = _addressFormState.asStateFlow()

    fun createAddress(address: String, phone:String) {
        viewModelScope.launch {
            _addressFormState.value = AddressFormState.Loading
            val customerId = dataStore.getUserId().first()
            createAddressUseCase.invoke(address,phone,customerId).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _addressFormState.value = AddressFormState.CreateAddressSuccess(response.data)
                    is UseCaseResult.Error -> _addressFormState.value = AddressFormState.Error(response.message)
                }
            }
        }
    }

    fun updateAddress(id:Int,address:String) {
        viewModelScope.launch {
            _addressFormState.value = AddressFormState.Loading
            val customerId = dataStore.getUserId().first()
            updateAddressUseCase.invoke(customerId.toInt(),address,id).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _addressFormState.value = AddressFormState.UpdateAddressSuccess(response.data)
                    is UseCaseResult.Error -> _addressFormState.value = AddressFormState.Error(response.message)
                }
            }
        }
    }
}
