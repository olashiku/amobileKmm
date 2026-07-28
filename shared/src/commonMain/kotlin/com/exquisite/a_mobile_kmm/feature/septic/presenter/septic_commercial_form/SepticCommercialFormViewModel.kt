package com.exquisite.a_mobile_kmm.feature.septic.presenter.septic_commercial_form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.septic.domain.model.SendEnquiryModel
import com.exquisite.a_mobile_kmm.feature.septic.domain.usecase.GetSepticTruckSizeUseCase
import com.exquisite.a_mobile_kmm.feature.septic.domain.usecase.SendEnquiryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SepticCommercialFormViewModel(
    private val sendEnquiryUseCase: SendEnquiryUseCase,
    private val getSepticTruckSizeUseCase: GetSepticTruckSizeUseCase
) : ViewModel() {

    private var _septicCommercialFormState = MutableStateFlow<SepticCommercialFormState>(SepticCommercialFormState.Idle)
    val septicCommercialFormState = _septicCommercialFormState.asStateFlow()


    private var _septicTruckSizeState = MutableStateFlow<SepticTruckSizeState>(SepticTruckSizeState.Idle)
    val septicTruckSizeState = _septicTruckSizeState.asStateFlow()

init{
    getSepticTruckSize()
}
    fun sendEnquiry(request: SendEnquiryModel) {
        viewModelScope.launch {
            _septicCommercialFormState.value = SepticCommercialFormState.Loading
            sendEnquiryUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _septicCommercialFormState.value = SepticCommercialFormState.SendEnquirySuccess(response.data)
                    is UseCaseResult.Error -> _septicCommercialFormState.value = SepticCommercialFormState.Error(response.message)
                }
            }
        }
    }

    fun getSepticTruckSize() {
        viewModelScope.launch {
            _septicTruckSizeState.value = SepticTruckSizeState.Loading
            getSepticTruckSizeUseCase.invoke().collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _septicTruckSizeState.value = SepticTruckSizeState.GetTruckSizeSuccess(response.data)
                    is UseCaseResult.Error -> _septicTruckSizeState.value = SepticTruckSizeState.Error(response.message)
                }
            }
        }
    }
}
