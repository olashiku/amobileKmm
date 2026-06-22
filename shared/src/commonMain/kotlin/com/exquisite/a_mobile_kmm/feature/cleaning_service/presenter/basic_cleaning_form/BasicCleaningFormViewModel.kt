package com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.basic_cleaning_form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.GetBasicCleaningLocationsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BasicCleaningFormViewModel(
    private val getBasicCleaningLocationsUseCase: GetBasicCleaningLocationsUseCase
) : ViewModel() {

    private var _basicCleaningFormState = MutableStateFlow<BasicCleaningFormState>(BasicCleaningFormState.Idle)
    val basicCleaningFormState = _basicCleaningFormState.asStateFlow()

    fun getBasicCleaningLocations() {
        viewModelScope.launch {
            _basicCleaningFormState.value = BasicCleaningFormState.Loading
            getBasicCleaningLocationsUseCase.invoke().collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _basicCleaningFormState.value = BasicCleaningFormState.Success(response.data)
                    is UseCaseResult.Error -> _basicCleaningFormState.value = BasicCleaningFormState.Error(response.message)
                }
            }
        }
    }
}
