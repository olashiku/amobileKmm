package com.exquisite.a_mobile_kmm.feature.pest_control.presenter.pest_control_commercial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.screen_components.DateModel
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.PestControlCommercialModel
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.RequestCommercialPestControlModel
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.usecase.RequestCommercialPestControlUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PestControlCommercialViewModel(
    private val requestCommercialPestControlUseCase: RequestCommercialPestControlUseCase,
    private val dataStore: AMobileDataStore
) : ViewModel() {

    private var _pestControlCommercialState =
        MutableStateFlow<PestControlCommercialState>(PestControlCommercialState.Idle)
    val pestControlCommercialState = _pestControlCommercialState.asStateFlow()

    private val _formData = MutableStateFlow(PestControlCommercialModel())
    val formData = _formData.asStateFlow()


    fun requestCommercialPestControl(
        companyName: String,
        companyEmail: String,
        companyAddress: String,
        availabilityDate: String,
        availabilityTime: String,
        recipientName: String,
        recipientEmail: String,
        recipientPhone: String
    ) {
        viewModelScope.launch {
            val customerId = dataStore.getUserId().first()
            val request = RequestCommercialPestControlModel(
                customerId.toInt(),
                companyName,
                companyEmail,
                companyAddress,
                availabilityDate,
                availabilityTime,
                recipientName,
                recipientEmail,
                recipientPhone
            )
            _pestControlCommercialState.value = PestControlCommercialState.Loading
            requestCommercialPestControlUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _pestControlCommercialState.value =
                        PestControlCommercialState.Success(response.data)

                    is UseCaseResult.Error -> _pestControlCommercialState.value =
                        PestControlCommercialState.Error(response.message)
                }
            }
        }
    }

    fun updateFormData(update: (PestControlCommercialModel) -> PestControlCommercialModel) {
        _formData.value = update(_formData.value)
    }

    fun setCompanyName(name: String) {
        _formData.value = _formData.value.copy(companyName = name)
    }

    fun setCompanyEmail(email: String) {
        _formData.value = _formData.value.copy(companyEmail = email)
    }

    fun setAddress(address: String) {
        _formData.value = _formData.value.copy(address = address)
    }

    fun setSelectedDate(date: DateModel?) {
        _formData.value = _formData.value.copy(selectedDate = date)
    }

    fun setSelectedTime(time: String?) {
        _formData.value = _formData.value.copy(selectedTime = time)
    }

    fun setRecipientName(name: String) {
        _formData.value = _formData.value.copy(recipientName = name)
    }

    fun setRecipientEmail(email: String) {
        _formData.value = _formData.value.copy(recipientEmail = email)
    }

    fun setRecipientPhone(phone: String) {
        _formData.value = _formData.value.copy(recipientPhone = phone)
    }

    fun clearState() {
        _formData.value = PestControlCommercialModel()
        _pestControlCommercialState.value = PestControlCommercialState.Idle

    }

}
