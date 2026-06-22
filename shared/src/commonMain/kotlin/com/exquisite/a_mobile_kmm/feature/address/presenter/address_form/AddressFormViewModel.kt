package com.exquisite.a_mobile_kmm.feature.address.presenter.address_form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.address.data.remote.request.CreateAddressRequestDto
import com.exquisite.a_mobile_kmm.feature.address.data.remote.request.UpdateAddressRequestDto
import com.exquisite.a_mobile_kmm.feature.address.domain.usecase.CreateAddressUseCase
import com.exquisite.a_mobile_kmm.feature.address.domain.usecase.UpdateAddressUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddressFormViewModel(
    private val createAddressUseCase: CreateAddressUseCase,
    private val updateAddressUseCase: UpdateAddressUseCase
) : ViewModel() {

    private var _addressFormState = MutableStateFlow<AddressFormState>(AddressFormState.Idle)
    val addressFormState = _addressFormState.asStateFlow()

    fun createAddress(request: CreateAddressRequestDto) {
        viewModelScope.launch {
            _addressFormState.value = AddressFormState.Loading
            createAddressUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _addressFormState.value = AddressFormState.CreateAddressSuccess(response.data)
                    is UseCaseResult.Error -> _addressFormState.value = AddressFormState.Error(response.message)
                }
            }
        }
    }

    fun updateAddress(request: UpdateAddressRequestDto) {
        viewModelScope.launch {
            _addressFormState.value = AddressFormState.Loading
            updateAddressUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _addressFormState.value = AddressFormState.UpdateAddressSuccess(response.data)
                    is UseCaseResult.Error -> _addressFormState.value = AddressFormState.Error(response.message)
                }
            }
        }
    }
}
