package com.exquisite.a_mobile_kmm.feature.employee.presenter.home

import com.exquisite.a_mobile_kmm.feature.employee.domain.model.AgentServiceCounts

sealed class ServiceCountsUiState {
    data object Initial : ServiceCountsUiState()
    data object Loading : ServiceCountsUiState()
    data class Success(val data: AgentServiceCounts) : ServiceCountsUiState()
    data class Error(val message: String) : ServiceCountsUiState()
}
