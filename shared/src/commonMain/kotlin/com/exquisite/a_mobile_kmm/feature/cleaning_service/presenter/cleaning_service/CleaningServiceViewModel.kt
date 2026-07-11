package com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.cleaning_service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.CheckBasicCleaningEligibilityUseCase
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.FindAllRegionsUseCase
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.FindApartmentTypeUseCase
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.FindCleaningTypeUseCase
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.FindLocationByRegionUseCase
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.FindNumberOfRoomsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CleaningServiceViewModel(
    private val checkBasicCleaningEligibilityUseCase: CheckBasicCleaningEligibilityUseCase,
      private val dataStore: AMobileDataStore
) : ViewModel() {

    private var _cleaningServiceState = MutableStateFlow<CleaningServiceState>(CleaningServiceState.Idle)
    val cleaningServiceState = _cleaningServiceState.asStateFlow()




    fun checkBasicCleaningEligibility() {
        viewModelScope.launch {
            val customerId = dataStore.getUserId().first()
            _cleaningServiceState.value = CleaningServiceState.Loading
            checkBasicCleaningEligibilityUseCase.invoke(customerId.toInt()).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _cleaningServiceState.value = CleaningServiceState.Success(response.data)
                    is UseCaseResult.Error -> _cleaningServiceState.value = CleaningServiceState.Error(response.message)
                }
            }
        }
    }

    fun resetState() {
        _cleaningServiceState.value = CleaningServiceState.Idle
    }
}
