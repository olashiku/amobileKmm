package com.exquisite.a_mobile_kmm.feature.janitorial.presenter.janitorial

import com.exquisite.a_mobile_kmm.feature.janitorial.domain.model.JanitorialResponseModel

sealed class JanitorialState {
    data object Idle : JanitorialState()
    data object Loading : JanitorialState()
    data class Success(val response: JanitorialResponseModel) : JanitorialState()
    data class Error(val message: String) : JanitorialState()
}
