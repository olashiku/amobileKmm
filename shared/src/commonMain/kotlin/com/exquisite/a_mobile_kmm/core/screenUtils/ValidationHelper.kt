package com.exquisite.a_mobile_kmm.core.screenUtils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String = ""
)

object ValidationHelper {
    
    fun validateEmail(email: String): ValidationResult {
        val emailRegex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$".toRegex()
        return when {
            email.isEmpty() -> ValidationResult(
                false,
                "Email is required"
            )
            !email.matches(emailRegex) ->
                ValidationResult(
                    false,
                    "Please enter a valid email address"
                )
            else -> ValidationResult(
                true
            )
        }
    }
    
    fun validatePassword(password: String): ValidationResult {
        return when {
            password.isEmpty() -> ValidationResult(
                false,
                "Password is required"
            )
            password.length < 8 -> ValidationResult(
                false,
                "Password must be at least 8 characters"
            )
            !password.any { it.isUpperCase() } -> ValidationResult(
                false,
                "Password must contain at least one uppercase letter"
            )
            !password.any { it.isLowerCase() } -> ValidationResult(
                false,
                "Password must contain at least one lowercase letter"
            )
            !password.any { it.isDigit() } -> ValidationResult(
                false,
                "Password must contain at least one number"
            )
            else -> ValidationResult(
                true
            )
        }
    }
    
    fun validateFirstName(name: String): ValidationResult {
        return when {
            name.isEmpty() -> ValidationResult(
                false,
                "First name is required"
            )
            name.length < 2 -> ValidationResult(
                false,
                "First name must be at least 2 characters"
            )
            !name.all { it.isLetter() || it.isWhitespace() } ->
                ValidationResult(
                    false,
                    "First name can only contain letters"
                )
            else -> ValidationResult(
                true
            )
        }
    }
    
    fun validateLastName(name: String): ValidationResult {
        return when {
            name.isEmpty() -> ValidationResult(
                false,
                "Last name is required"
            )
            name.length < 2 -> ValidationResult(
                false,
                "Last name must be at least 2 characters"
            )
            !name.all { it.isLetter() || it.isWhitespace() } ->
                ValidationResult(
                    false,
                    "Last name can only contain letters"
                )
            else -> ValidationResult(
                true
            )
        }
    }

    fun validateAddress(address: String): ValidationResult {
        return when {
            address.isEmpty() -> ValidationResult(
                false,
                "Address is required"
            )
            address.length < 2 -> ValidationResult(
                false,
                "Address must be at least 2 characters"
            )
            else -> ValidationResult(
                true
            )
        }
    }
    
    fun validatePhoneNumber(phone: String): ValidationResult {
        val cleanPhone = phone.replace(Regex("[\\s\\-\\(\\)]"), "")
        return when {
            phone.isEmpty() -> ValidationResult(
                false,
                "Phone number is required"
            )
            cleanPhone.length < 10 -> ValidationResult(
                false,
                "Phone number must be at least 10 digits"
            )
            !cleanPhone.all { it.isDigit() || it == '+' } ->
                ValidationResult(
                    false,
                    "Please enter a valid phone number"
                )
            else -> ValidationResult(
                true
            )
        }
    }
}

class FieldValidator(
    private val validator: (String) -> ValidationResult
) {
    private val _value = mutableStateOf("")
    private val _isValid = mutableStateOf(true)
    private val _errorMessage = mutableStateOf("")
    private val _hasBeenTouched = mutableStateOf(false)
    
    val value: MutableState<String> = _value
    val isValid: MutableState<Boolean> = _isValid
    val errorMessage: MutableState<String> = _errorMessage
    val hasBeenTouched: MutableState<Boolean> = _hasBeenTouched
    
    fun setValue(newValue: String) {
        _value.value = newValue
        _hasBeenTouched.value = true
        validate()
    }
    
    private fun validate() {
        if (_hasBeenTouched.value) {
            val result = validator(_value.value)
            _isValid.value = result.isValid
            _errorMessage.value = result.errorMessage
        }
    }
    
    fun forceValidation(): Boolean {
        _hasBeenTouched.value = true
        validate()
        return _isValid.value
    }
}