package com.exquisite.a_mobile_kmm.feature.septic.presenter.septic_residential_form2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.screen_components.DateModel
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.septic.domain.model.SepticResidentialForm2Model
import com.exquisite.a_mobile_kmm.feature.septic.domain.usecase.GetSepticTruckSizeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SepticResidentialFormViewModel2() : ViewModel() {

    private var _septicResidentialFormState = MutableStateFlow<SepticResidentialFormState>(SepticResidentialFormState.Idle)
    val septicResidentialFormState = _septicResidentialFormState.asStateFlow()

    private val _persistedFormData = MutableStateFlow(SepticResidentialForm2Model())
    val persistedFormData = _persistedFormData.asStateFlow()

    private val _selectedDate = MutableStateFlow<DateModel?>(_persistedFormData.value.selectedDate)
    val selectedDate = _selectedDate.asStateFlow()

    private val _selectedTime = MutableStateFlow<String?>(_persistedFormData.value.selectedTime)
    val selectedTime = _selectedTime.asStateFlow()

    fun setSelectedDate(date: DateModel?) {
        _selectedDate.value = date
        _persistedFormData.value = _persistedFormData.value.copy(selectedDate = date)
    }

    fun setSelectedTime(time: String?) {
        _selectedTime.value = time
        _persistedFormData.value = _persistedFormData.value.copy(selectedTime = time)
    }

    fun updateFullName(fullName: String) {
        _persistedFormData.value = _persistedFormData.value.copy(fullName = fullName)
    }

    fun updateEmail(email: String) {
        _persistedFormData.value = _persistedFormData.value.copy(email = email)
    }

    fun updatePhone(phone: String) {
        _persistedFormData.value = _persistedFormData.value.copy(phone = phone)
    }

    fun updateAddress(address: String) {
        _persistedFormData.value = _persistedFormData.value.copy(address = address)
    }

    fun updateAdditionalMessage(message: String) {
        _persistedFormData.value = _persistedFormData.value.copy(additionalMessage = message)
    }


}
