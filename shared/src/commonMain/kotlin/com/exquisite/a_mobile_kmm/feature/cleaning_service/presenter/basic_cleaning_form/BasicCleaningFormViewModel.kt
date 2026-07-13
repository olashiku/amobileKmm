package com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.basic_cleaning_form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.*
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BasicCleaningFormViewModel(
    private val findNumberOfRoomsUseCase: FindNumberOfRoomsUseCase,
    private val getBasicCleaningBreakdownUseCase: GetBasicCleaningBreakdownUseCase,
   private val dataStore: AMobileDataStore
) : ViewModel() {

    private var _basicCleaningFormState = MutableStateFlow<BasicCleaningFormState>(BasicCleaningFormState.Idle)
    val basicCleaningFormState = _basicCleaningFormState.asStateFlow()

    private val _selectedTime = MutableStateFlow<String?>(null)
    val selectedTime = _selectedTime.asStateFlow()

    private val _numberOfRooms = MutableStateFlow<List<NumberOfRoomsModel>>(emptyList())
    val numberOfRooms = _numberOfRooms.asStateFlow()

    private val _persistedFormData = MutableStateFlow(BasicCleaningFormModel())
    val persistedFormData = _persistedFormData.asStateFlow()

    private val _isNumberOfRoomsLoading = MutableStateFlow(false)
    val isNumberOfRoomsLoading = _isNumberOfRoomsLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

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



    fun setSelectedTime(time: String?) {
        _selectedTime.value = time
    }

    fun getBasicCleaningBreakdown(
        numberOfRooms: Int,
        cleaningTime: String,
        cleaningDate: List<String>
    ) {
        viewModelScope.launch {
            val customerId = dataStore.getUserId().first()
            val request = GetBasicCleaningBreakdownRequest(
                numberOfRooms = numberOfRooms,
                cleaningTime = cleaningTime,
                customerId = customerId.toInt(),
                cleaningDate = cleaningDate
            )
            _basicCleaningFormState.value = BasicCleaningFormState.Loading
            getBasicCleaningBreakdownUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> {
                        _basicCleaningFormState.value = BasicCleaningFormState.Success(response.data)
                    }
                    is UseCaseResult.Error -> {
                        _basicCleaningFormState.value = BasicCleaningFormState.Error(response.message)
                    }
                }
            }
        }
    }

    fun saveFormData(basicCleaningFormModel:BasicCleaningFormModel){
        _persistedFormData.value = basicCleaningFormModel
    }

    fun resetState(){
        _basicCleaningFormState.value = BasicCleaningFormState.Idle
        _errorMessage.value = null
    }
}
