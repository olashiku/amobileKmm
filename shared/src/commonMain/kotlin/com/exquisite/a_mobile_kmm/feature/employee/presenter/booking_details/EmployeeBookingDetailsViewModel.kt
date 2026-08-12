package com.exquisite.a_mobile_kmm.feature.employee.presenter.booking_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.auth.domain.usecase.UploadFileUseCase
import com.exquisite.a_mobile_kmm.feature.auth.presenter.upload_image.ImageUploadState
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.RateAndReviewRequest
import com.exquisite.a_mobile_kmm.feature.booking.domain.usecase.GetCleaningBookingUseCase
import com.exquisite.a_mobile_kmm.feature.booking.domain.usecase.GetPestControlBookingUseCase
import com.exquisite.a_mobile_kmm.feature.booking.domain.usecase.GetSepticBookingUseCase
import com.exquisite.a_mobile_kmm.feature.booking.domain.usecase.GetToiletBookingUseCase
import com.exquisite.a_mobile_kmm.feature.booking.domain.usecase.RateAndReviewUseCase
import com.exquisite.a_mobile_kmm.feature.employee.domain.model.BookingUpdateType
import com.exquisite.a_mobile_kmm.feature.employee.domain.model.UpdateAgentBookingRequest
import com.exquisite.a_mobile_kmm.feature.employee.domain.model.UpdateBookingImagesRequest
import com.exquisite.a_mobile_kmm.feature.employee.domain.usecase.UpdateAgentBookingUseCase
import com.exquisite.a_mobile_kmm.feature.employee.domain.usecase.UpdateBookingImagesUseCase
import com.exquisite.a_mobile_kmm.feature.employee.presenter.booking.UpdateBookingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EmployeeBookingDetailsViewModel(
    private val getCleaningBookingUseCase: GetCleaningBookingUseCase,
    private val getSepticBookingUseCase: GetSepticBookingUseCase,
    private val getPestControlBookingUseCase: GetPestControlBookingUseCase,
    private val getToiletBookingUseCase: GetToiletBookingUseCase,
    private val rateAndReviewUseCase: RateAndReviewUseCase,
    private val dataStore: AMobileDataStore,
    private val uploadFileUseCase: UploadFileUseCase,
    private val updateAgentBookingUseCase: UpdateAgentBookingUseCase,
    private val updateBookingImagesUseCase: UpdateBookingImagesUseCase,

    ) : ViewModel() {

    private var _bookingDetailsState = MutableStateFlow<BookingDetailsState>(BookingDetailsState.Idle)
    val bookingDetailsState = _bookingDetailsState.asStateFlow()

     private val _rateReviewState = MutableStateFlow<RateReviewState>(RateReviewState.Idle)
    val rateReviewState = _rateReviewState.asStateFlow()

    private val _updateState = MutableStateFlow<UpdateBookingUiState>(UpdateBookingUiState.Idle)
    val updateState = _updateState.asStateFlow()


    private val _imageUploadState = MutableStateFlow<ImageUploadState>(ImageUploadState.Idle)
    val imageUploadState = _imageUploadState.asStateFlow()

    private val _updateImagesState = MutableStateFlow<UpdateBookingImagesState>(UpdateBookingImagesState.Idle)
    val updateImagesState = _updateImagesState.asStateFlow()

    // Cache to track already loaded booking IDs
    private var cachedBookingId: Int? = null
    private var cachedBookingType: String? = null


    fun loadCleaningBooking(bookingId: Int) {
        viewModelScope.launch {
            _bookingDetailsState.value = BookingDetailsState.Loading
            try {
                val response = getCleaningBookingUseCase.invoke(bookingId).first()
                when (response) {
                    is UseCaseResult.Success -> {
                        _bookingDetailsState.value = BookingDetailsState.CleaningBookingSuccess(response.data)
                    }

                    is UseCaseResult.Error ->
                        _bookingDetailsState.value = BookingDetailsState.Error(response.message)
                }
            } catch (e: Exception) {
                _bookingDetailsState.value = BookingDetailsState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun loadSepticBooking(bookingId: Int) {
        // Skip if already loaded
        if (cachedBookingId == bookingId && cachedBookingType == "SEPTIC" &&
            _bookingDetailsState.value is BookingDetailsState.SepticBookingSuccess) {
            return
        }

        viewModelScope.launch {
            _bookingDetailsState.value = BookingDetailsState.Loading
            try {
                val response = getSepticBookingUseCase.invoke(bookingId).first()
                when (response) {
                    is UseCaseResult.Success -> {
                        _bookingDetailsState.value =
                            BookingDetailsState.SepticBookingSuccess(response.data)
                        cachedBookingId = bookingId
                        cachedBookingType = "SEPTIC"
                    }

                    is UseCaseResult.Error ->
                        _bookingDetailsState.value = BookingDetailsState.Error(response.message)
                }
            } catch (e: Exception) {
                _bookingDetailsState.value = BookingDetailsState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun loadPestControlBooking(bookingId: Int) {
        // Skip if already loaded
        if (cachedBookingId == bookingId && cachedBookingType == "PEST_CONTROL" &&
            _bookingDetailsState.value is BookingDetailsState.PestControlBookingSuccess) {
            return
        }

        viewModelScope.launch {
            _bookingDetailsState.value = BookingDetailsState.Loading
            try {
                val response = getPestControlBookingUseCase.invoke(bookingId).first()
                when (response) {
                    is UseCaseResult.Success -> {
                        _bookingDetailsState.value =
                            BookingDetailsState.PestControlBookingSuccess(response.data)
                        cachedBookingId = bookingId
                        cachedBookingType = "PEST_CONTROL"
                    }

                    is UseCaseResult.Error ->
                        _bookingDetailsState.value = BookingDetailsState.Error(response.message)
                }
            } catch (e: Exception) {
                _bookingDetailsState.value = BookingDetailsState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun loadToiletBooking(bookingId: Int) {
        // Skip if already loaded
        if (cachedBookingId == bookingId && cachedBookingType == "TOILET" &&
            _bookingDetailsState.value is BookingDetailsState.ToiletBookingSuccess) {
            return
        }

        viewModelScope.launch {
            _bookingDetailsState.value = BookingDetailsState.Loading
            try {
                val response = getToiletBookingUseCase.invoke(bookingId).first()
                when (response) {
                    is UseCaseResult.Success -> {
                        _bookingDetailsState.value =
                            BookingDetailsState.ToiletBookingSuccess(response.data)
                        cachedBookingId = bookingId
                        cachedBookingType = "TOILET"
                    }

                    is UseCaseResult.Error ->
                        _bookingDetailsState.value = BookingDetailsState.Error(response.message)
                }
            } catch (e: Exception) {
                _bookingDetailsState.value = BookingDetailsState.Error(e.message ?: "Unknown error")
            }
        }
    }



    fun updateBooking(
        bookingId: Int,
        updateType: BookingUpdateType,
        agentRemark: String
    ) {
        viewModelScope.launch {
            _updateState.value = UpdateBookingUiState.Loading

            // Get employee ID from datastore
            val employeeId = dataStore.getUserId().first().toIntOrNull()

            if (employeeId == null) {
                _updateState.value = UpdateBookingUiState.Error("Employee ID not found")
                return@launch
            }

            val request = UpdateAgentBookingRequest(
                employeeId = employeeId,
                bookingId = bookingId,
                updateType = updateType,
                agentRemark = agentRemark
            )

            updateAgentBookingUseCase(request).collect { result ->
                _updateState.value = when (result) {
                    is com.exquisite.a_mobile_kmm.core.network.Result.Success -> {
                        result.data?.let {
                            if (it.isSuccess) {
                                UpdateBookingUiState.Success(it.message)
                            } else {
                                UpdateBookingUiState.Error(it.message)
                            }
                        } ?: UpdateBookingUiState.Error("No response data")
                    }
                    is Result.Exception -> {
                        UpdateBookingUiState.Error(
                            result.exception.message ?: "An error occurred"
                        )
                    }
                }
            }
        }
    }

    fun uploadImage(image: ByteArray, fileName: String) {
        viewModelScope.launch {
            _imageUploadState.value = ImageUploadState.Loading
            uploadFileUseCase.invoke(image, fileName)
                .collect { result ->
                    when (result) {
                        is UseCaseResult.Success -> {
                            _imageUploadState.value = ImageUploadState.Success(result.data)
                        }
                        is UseCaseResult.Error -> {
                            _imageUploadState.value = ImageUploadState.Error(result.message)
                        }
                        else -> {}
                    }
                }
        }
    }

    fun updateBookingImages(bookingId: Int, images: List<String>) {
        viewModelScope.launch {
            _updateImagesState.value = UpdateBookingImagesState.Loading

            val employeeId = dataStore.getUserId().first().toIntOrNull()

            if (employeeId == null) {
                _updateImagesState.value = UpdateBookingImagesState.Error("Employee ID not found")
                return@launch
            }

            val request = UpdateBookingImagesRequest(
                employeeId = employeeId,
                bookingId = bookingId,
                images = images
            )

            updateBookingImagesUseCase(request).collect { result ->
                _updateImagesState.value = when (result) {
                    is Result.Success -> {
                        result.data?.let {
                            if (it.isSuccess) {
                                UpdateBookingImagesState.Success(it.message)
                            } else {
                                UpdateBookingImagesState.Error(it.message)
                            }
                        } ?: UpdateBookingImagesState.Error("No response data")
                    }
                    is Result.Exception -> {
                        UpdateBookingImagesState.Error(
                            result.exception.message ?: "An error occurred"
                        )
                    }
                }
            }
        }
    }

    fun clearState() {
        _bookingDetailsState.value = BookingDetailsState.Idle
        cachedBookingId = null
        cachedBookingType = null
    }


}
