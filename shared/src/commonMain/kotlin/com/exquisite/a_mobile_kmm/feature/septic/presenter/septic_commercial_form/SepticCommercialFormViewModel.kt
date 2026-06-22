package com.exquisite.a_mobile_kmm.feature.septic.presenter.septic_commercial_form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.septic.data.remote.request.SendEnquiryRequestDto
import com.exquisite.a_mobile_kmm.feature.septic.domain.usecase.SendEnquiryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SepticCommercialFormViewModel(
    private val sendEnquiryUseCase: SendEnquiryUseCase
) : ViewModel() {

    private var _septicCommercialFormState = MutableStateFlow<SepticCommercialFormState>(SepticCommercialFormState.Idle)
    val septicCommercialFormState = _septicCommercialFormState.asStateFlow()

    fun sendEnquiry(request: SendEnquiryRequestDto) {
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
}
