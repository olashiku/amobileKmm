package com.exquisite.a_mobile_kmm.feature.janitorial.presenter.janitorial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.janitorial.data.remote.request.CreateJanitorialRequestDto
import com.exquisite.a_mobile_kmm.feature.janitorial.domain.usecase.CreateJanitorialUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class JanitorialViewModel(
    private val createJanitorialUseCase: CreateJanitorialUseCase
) : ViewModel() {

    private var _janitorialState = MutableStateFlow<JanitorialState>(JanitorialState.Idle)
    val janitorialState = _janitorialState.asStateFlow()

    fun createJanitorial(request: CreateJanitorialRequestDto) {
        viewModelScope.launch {
            _janitorialState.value = JanitorialState.Loading
            createJanitorialUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _janitorialState.value = JanitorialState.Success(response.data)
                    is UseCaseResult.Error -> _janitorialState.value = JanitorialState.Error(response.message)
                }
            }
        }
    }
}
