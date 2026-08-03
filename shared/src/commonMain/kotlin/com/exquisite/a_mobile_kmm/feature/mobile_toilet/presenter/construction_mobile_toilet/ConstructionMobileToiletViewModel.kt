package com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.construction_mobile_toilet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.screen_components.DateModel
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.ConstructionToiletModel
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.ConstructionToiletRequestModel
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.usecase.RequestForConstructionUseCase
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.PestControlCommercialModel
import com.exquisite.a_mobile_kmm.feature.pest_control.presenter.pest_control_commercial.PestControlCommercialState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ConstructionMobileToiletViewModel(
    private val requestForConstructionUseCase: RequestForConstructionUseCase,
    private val dataStore : AMobileDataStore
) : ViewModel() {

    private val _formData = MutableStateFlow(ConstructionToiletModel())
    val formData = _formData.asStateFlow()

    private var _constructionMobileToiletState = MutableStateFlow<ConstructionMobileToiletState>(ConstructionMobileToiletState.Idle)
    val constructionMobileToiletState = _constructionMobileToiletState.asStateFlow()


    fun requestForConstruction(
         companyName: String = "",
         companyEmail: String= "",
         constructionAddress: String= "",
         availabilityDate: String= "",
         availabilityTime: String= "",
         recipientName: String= "",
         recipientEmail: String= "",
         recipientPhone: String= "",
         numberOfPeopleOnSite: String= "",
         numberOfMonths: String= ""
    ) {
        viewModelScope.launch {
            val customerId = dataStore.getUserId().first()
           val  request=  ConstructionToiletRequestModel(customerId.toInt(),
               companyName,companyEmail,constructionAddress,availabilityDate,
               availabilityTime,recipientName,recipientEmail,
               recipientPhone,numberOfPeopleOnSite,numberOfMonths
               )
            _constructionMobileToiletState.value = ConstructionMobileToiletState.Loading
            requestForConstructionUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _constructionMobileToiletState.value = ConstructionMobileToiletState.Success(response.data)
                    is UseCaseResult.Error -> _constructionMobileToiletState.value = ConstructionMobileToiletState.Error(response.message)
                }
            }
        }
    }


    fun setCompanyName(name: String) {
        _formData.value = _formData.value.copy(companyName = name)
    }

    fun setCompanyEmail(email: String) {
        _formData.value = _formData.value.copy(companyEmail = email)
    }

    fun setAddress(address: String) {
        _formData.value = _formData.value.copy(constructionAddress = address)
    }

    fun setSelectedDate(date: DateModel?) {
        _formData.value = _formData.value.copy(availabilityDate = date)
    }

    fun setSelectedTime(time: String?) {
        _formData.value = _formData.value.copy(availabilityTime = time)
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

    fun setNumberOfPeopleOnSite(count: String) {
        _formData.value = _formData.value.copy(numberOfPeopleOnSite = count)
    }

    fun setDuration(months: String) {
        _formData.value = _formData.value.copy(numberOfMonths = months)
    }

    fun clearState() {
        _formData.value = ConstructionToiletModel()
        _constructionMobileToiletState.value = ConstructionMobileToiletState.Idle
    }

    fun reset(){
        _constructionMobileToiletState.value = ConstructionMobileToiletState.Idle

    }
}
