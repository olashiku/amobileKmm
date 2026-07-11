package com.exquisite.a_mobile_kmm.feature.auth.presenter.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.auth.domain.model.SignupFormData
import com.exquisite.a_mobile_kmm.feature.auth.domain.usecase.InitRegisterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignupViewModel(private val registerUseCase: InitRegisterUseCase) : ViewModel() {

    private var registrationState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    var _registrationState = registrationState.asStateFlow()

    private var _rememberMeState = MutableStateFlow<Boolean>(false)
    val rememberMeState = _rememberMeState.asStateFlow()

    // Persisted form values
    private val _persistedFormData = MutableStateFlow(SignupFormData())
    val persistedFormData = _persistedFormData.asStateFlow()


    fun register(email: String, firstName: String, lastName: String, phone: String) {
        viewModelScope.launch {
            registrationState.value = RegisterState.Loading
            registerUseCase.invoke(email, firstName, lastName, phone).collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        registrationState.value = RegisterState.Success(response.data)

                    is UseCaseResult.Error -> registrationState.value = RegisterState.Error(response.message)
                }
            }
        }
    }

//    fun saveRememberMe(rememberMe: Boolean) {
//        viewModelScope.launch(Dispatchers.IO) {
//            registerUseCase.saveRememberMe(rememberMe)
//        }
//    }
//
//    fun getRememberMeState() {
//        viewModelScope.launch {
//            registerUseCase.getRememberMe().collect { value ->
//                _rememberMeState.value = value
//            }
//        }
//
//    }

    fun clearState() {
        registrationState.value = RegisterState.Idle
    }

    fun saveFormData(
        firstName: String,
        lastName: String,
        email: String,
        phone: String
    ) {
        _persistedFormData.value = SignupFormData(
            firstName = firstName,
            lastName = lastName,
            email = email,
            phone = phone
        )
    }

    fun clearFormData() {
        _persistedFormData.value = SignupFormData()
    }
}