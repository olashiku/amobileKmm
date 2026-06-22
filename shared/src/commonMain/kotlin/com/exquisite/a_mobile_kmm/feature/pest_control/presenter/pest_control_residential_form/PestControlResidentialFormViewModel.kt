package com.exquisite.a_mobile_kmm.feature.pest_control.presenter.pest_control_residential_form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.pest_control.data.remote.request.GetPestControlPriceRequestDto
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.usecase.GetPestControlPriceUseCase
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.usecase.GetServiceListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PestControlResidentialFormViewModel(
    private val getServiceListUseCase: GetServiceListUseCase,
    private val getPestControlPriceUseCase: GetPestControlPriceUseCase
) : ViewModel() {

    private var _pestControlResidentialFormState = MutableStateFlow<PestControlResidentialFormState>(PestControlResidentialFormState.Idle)
    val pestControlResidentialFormState = _pestControlResidentialFormState.asStateFlow()

    fun getServiceList() {
        viewModelScope.launch {
            _pestControlResidentialFormState.value = PestControlResidentialFormState.Loading
            getServiceListUseCase.invoke().collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _pestControlResidentialFormState.value = PestControlResidentialFormState.ServiceListSuccess(response.data)
                    is UseCaseResult.Error -> _pestControlResidentialFormState.value = PestControlResidentialFormState.Error(response.message)
                }
            }
        }
    }

    fun getPestControlPrice(request: GetPestControlPriceRequestDto) {
        viewModelScope.launch {
            _pestControlResidentialFormState.value = PestControlResidentialFormState.Loading
            getPestControlPriceUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _pestControlResidentialFormState.value = PestControlResidentialFormState.PriceSuccess(response.data)
                    is UseCaseResult.Error -> _pestControlResidentialFormState.value = PestControlResidentialFormState.Error(response.message)
                }
            }
        }
    }
}
