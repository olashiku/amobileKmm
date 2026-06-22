package com.exquisite.a_mobile_kmm.feature.pest_control.presenter.pest_control_commercial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.pest_control.data.remote.request.RequestCommercialPestControlRequestDto
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.usecase.RequestCommercialPestControlUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PestControlCommercialViewModel(
    private val requestCommercialPestControlUseCase: RequestCommercialPestControlUseCase
) : ViewModel() {

    private var _pestControlCommercialState = MutableStateFlow<PestControlCommercialState>(PestControlCommercialState.Idle)
    val pestControlCommercialState = _pestControlCommercialState.asStateFlow()

    fun requestCommercialPestControl(request: RequestCommercialPestControlRequestDto) {
        viewModelScope.launch {
            _pestControlCommercialState.value = PestControlCommercialState.Loading
            requestCommercialPestControlUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _pestControlCommercialState.value = PestControlCommercialState.Success(response.data)
                    is UseCaseResult.Error -> _pestControlCommercialState.value = PestControlCommercialState.Error(response.message)
                }
            }
        }
    }
}
