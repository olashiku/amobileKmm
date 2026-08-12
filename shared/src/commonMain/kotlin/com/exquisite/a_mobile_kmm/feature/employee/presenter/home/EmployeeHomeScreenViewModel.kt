package com.exquisite.a_mobile_kmm.feature.employee.presenter.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.feature.employee.domain.usecase.GetAgentServiceCountsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EmployeeHomeScreenViewModel(
    private val dataStore: AMobileDataStore,
    private val getAgentServiceCountsUseCase: GetAgentServiceCountsUseCase
) : ViewModel() {

    private val _customerName = MutableStateFlow("")
    val customerName = _customerName.asStateFlow()

    private val _profilePicture = MutableStateFlow("")
    val profilePicture = _profilePicture.asStateFlow()

    private val _serviceCountsState =
        MutableStateFlow<ServiceCountsUiState>(ServiceCountsUiState.Initial)
    val serviceCountsState = _serviceCountsState.asStateFlow()


     fun getCustomerName() {
        viewModelScope.launch {
            dataStore.getCustomerName().collect {
                _customerName.value = it
            }
        }
    }

     fun getProfilePicture() {
        viewModelScope.launch {
            dataStore.getProfilePicture().collect {
                _profilePicture.value = it
            }
        }
    }

     fun fetchServiceCounts() {
        viewModelScope.launch {
            _serviceCountsState.value = ServiceCountsUiState.Loading

            val agentId = dataStore.getUserId().first().toInt()

            getAgentServiceCountsUseCase(agentId).collect { result ->
                _serviceCountsState.value = when (result) {
                    is Result.Success -> {
                        result.data?.let { ServiceCountsUiState.Success(it) }
                            ?: ServiceCountsUiState.Error("No data available")
                    }

                    is Result.Exception -> {
                        ServiceCountsUiState.Error(
                            result.exception.message ?: "An error occurred"
                        )
                    }
                }
            }
        }
    }
    fun retryFetchServiceCounts() {
        fetchServiceCounts()

    }
}

