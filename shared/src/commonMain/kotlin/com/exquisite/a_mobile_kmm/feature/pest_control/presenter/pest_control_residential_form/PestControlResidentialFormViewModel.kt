package com.exquisite.a_mobile_kmm.feature.pest_control.presenter.pest_control_residential_form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.FindNumberOfRoomsUseCase
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.GetPestControlPriceModel
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.PestControlResidentialFormModel
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.usecase.GetPestControlPriceUseCase
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.usecase.GetServiceListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PestControlResidentialFormViewModel(
    private val getServiceListUseCase: GetServiceListUseCase,
    private val getPestControlPriceUseCase: GetPestControlPriceUseCase,
    private val findNumberOfRoomsUseCase: FindNumberOfRoomsUseCase,
    private val dataStore: AMobileDataStore
) : ViewModel() {

    private var _pestControlResidentialFormState = MutableStateFlow<PestControlResidentialFormState>(PestControlResidentialFormState.Idle)
    val pestControlResidentialFormState = _pestControlResidentialFormState.asStateFlow()

    private val _numberOfRooms = MutableStateFlow<List<com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.NumberOfRoomsModel>>(emptyList())
    val numberOfRooms = _numberOfRooms.asStateFlow()

    private val _isNumberOfRoomsLoading = MutableStateFlow(false)
    val isNumberOfRoomsLoading = _isNumberOfRoomsLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _formData = MutableStateFlow(PestControlResidentialFormModel())
    val formData = _formData.asStateFlow()

    private var _pestControlServiceListState = MutableStateFlow<PestControlServiceListState>(PestControlServiceListState.Idle)
    val pestControlServiceListState = _pestControlServiceListState.asStateFlow()

    fun getServiceList() {
        viewModelScope.launch {
            _pestControlServiceListState.value = PestControlServiceListState.Loading
            getServiceListUseCase.invoke().collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _pestControlServiceListState.value = PestControlServiceListState.ServiceListSuccess(response.data)
                    is UseCaseResult.Error -> _pestControlServiceListState.value = PestControlServiceListState.Error(response.message)
                }
            }
        }
    }

    fun findNumberOfRooms() {
        viewModelScope.launch {
            _isNumberOfRoomsLoading.value = true
            findNumberOfRoomsUseCase.invoke().collect { response ->
                when (response) {
                    is UseCaseResult.Success -> {
                        _numberOfRooms.value = response.data
                        _isNumberOfRoomsLoading.value = false
                    }

                    is UseCaseResult.Error -> {
                        _errorMessage.value = response.message
                        _isNumberOfRoomsLoading.value = false
                    }
                }
            }
        }
    }

    fun getPestControlPrice(serviceId: Int, numberOfRoomsId: Int) {
        viewModelScope.launch {
            val customerId = dataStore.getUserId().first()
            val request =  GetPestControlPriceModel(numberOfRoomsId,serviceId,customerId.toInt(),)
            _pestControlResidentialFormState.value = PestControlResidentialFormState.Loading
            getPestControlPriceUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _pestControlResidentialFormState.value = PestControlResidentialFormState.PriceSuccess(response.data)
                    is UseCaseResult.Error -> _pestControlResidentialFormState.value = PestControlResidentialFormState.Error(response.message)
                }
            }
        }
    }

    fun setSelectedService(serviceName: String, serviceId: Int) {
        _formData.value = _formData.value.copy(
            selectedServiceName = serviceName,
            selectedServiceId = serviceId
        )
    }

    fun setSelectedRoom(roomName: String, roomId: Int) {
        _formData.value = _formData.value.copy(
            selectedRoomName = roomName,
            selectedRoomId = roomId
        )
    }

    fun clearFormData() {
        _pestControlResidentialFormState.value = PestControlResidentialFormState.Idle
    }
}
