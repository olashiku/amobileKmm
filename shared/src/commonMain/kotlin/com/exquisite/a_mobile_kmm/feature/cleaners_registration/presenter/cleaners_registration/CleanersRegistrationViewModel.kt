package com.exquisite.a_mobile_kmm.feature.cleaners_registration.presenter.cleaners_registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.data.remote.request.RegisterCleanerRequestDto
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.domain.usecase.RegisterCleanerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CleanersRegistrationViewModel(private val registerCleanerUseCase: RegisterCleanerUseCase) : ViewModel() {
    
    private var _cleanersRegistrationState = MutableStateFlow<CleanersRegistrationState>(CleanersRegistrationState.Idle)
    val cleanersRegistrationState = _cleanersRegistrationState.asStateFlow()
    
    fun registerCleaner(request: RegisterCleanerRequestDto) {
        viewModelScope.launch {
            _cleanersRegistrationState.value = CleanersRegistrationState.Loading
            registerCleanerUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _cleanersRegistrationState.value = CleanersRegistrationState.Success(response.data)
                    is UseCaseResult.Error ->
                        _cleanersRegistrationState.value = CleanersRegistrationState.Error(response.message)
                }
            }
        }
    }
    
    fun clearState() {
        _cleanersRegistrationState.value = CleanersRegistrationState.Idle
    }
}
