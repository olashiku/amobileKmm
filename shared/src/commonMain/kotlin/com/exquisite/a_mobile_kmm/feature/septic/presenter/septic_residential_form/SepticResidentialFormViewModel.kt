package com.exquisite.a_mobile_kmm.feature.septic.presenter.septic_residential_form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.septic.domain.usecase.GetSepticTruckSizeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SepticResidentialFormViewModel(
    private val getSepticTruckSizeUseCase: GetSepticTruckSizeUseCase
) : ViewModel() {

    private var _septicResidentialFormState = MutableStateFlow<SepticResidentialFormState>(SepticResidentialFormState.Idle)
    val septicResidentialFormState = _septicResidentialFormState.asStateFlow()

    fun getSepticTruckSize() {
        viewModelScope.launch {
            _septicResidentialFormState.value = SepticResidentialFormState.Loading
            getSepticTruckSizeUseCase.invoke().collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _septicResidentialFormState.value = SepticResidentialFormState.GetTruckSizeSuccess(response.data)
                    is UseCaseResult.Error -> _septicResidentialFormState.value = SepticResidentialFormState.Error(response.message)
                }
            }
        }
    }
}
