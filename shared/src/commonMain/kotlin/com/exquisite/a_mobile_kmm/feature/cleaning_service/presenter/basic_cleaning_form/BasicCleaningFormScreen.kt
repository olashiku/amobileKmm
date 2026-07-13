package com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.basic_cleaning_form

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exquisite.a_mobile_kmm.core.nav.NavigationUtils
import com.exquisite.a_mobile_kmm.core.screenUtils.FieldValidator
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationHelper
import com.exquisite.a_mobile_kmm.core.screenUtils.formatTime
import com.exquisite.a_mobile_kmm.core.screen_components.DateRange
import kotlinx.datetime.LocalDate
import com.exquisite.a_mobile_kmm.core.screen_components.FixedHeaderWithBackButton
import com.exquisite.a_mobile_kmm.core.screen_components.InfoBanner
import com.exquisite.a_mobile_kmm.core.screen_components.MonthlyCalendarRangeSelector
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.screen_components.TimeSlotGrid
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedDropdownField
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.BasicCleaningFormModel
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.LoadingDialog
import com.exquisite.dripp.core.components.rememberSnackBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BasicCleaningFormScreen(
    goBack: () -> Unit,goToPreview: (String,String) -> Unit,modifier :Modifier = Modifier,
    viewModel : BasicCleaningFormViewModel = koinViewModel<BasicCleaningFormViewModel>()
) {
    val numberOfRoomsModelData by viewModel.numberOfRooms.collectAsStateWithLifecycle()
    val persistedFormData by viewModel.persistedFormData.collectAsStateWithLifecycle()
    var numberOfRoomsId by remember { mutableStateOf(persistedFormData.numberOfRooms?.second?.toInt()?:0) }
    val isNumberOfRoomsLoading by viewModel.isNumberOfRoomsLoading.collectAsStateWithLifecycle()

   val basicCleaningFormState by viewModel.basicCleaningFormState.collectAsStateWithLifecycle()

    val (snackBar, snackBarHostState) = rememberSnackBar()

    val times = listOf("9:00 AM", "10:00 AM", "11:30 AM","12:00 PM", "1:00 PM", "2:30 PM", "4:00 PM")

    val selectedTime by viewModel.selectedTime.collectAsStateWithLifecycle()

    var dateRange by remember { mutableStateOf(DateRange()) }

    // Restore date range from persisted data
    LaunchedEffect(persistedFormData.cleaningDate) {
        if (persistedFormData.cleaningDate.isNotEmpty()) {
            val dates = persistedFormData.cleaningDate.map { LocalDate.parse(it) }
            val sortedDates = dates.sorted()
            val firstDate = sortedDates.first()
            val lastDate = sortedDates.last()

            dateRange = DateRange(
                startDate = firstDate.dayOfMonth,
                endDate = lastDate.dayOfMonth,
                year = firstDate.year,
                month = firstDate.monthNumber
            )
        }
    }

    val numberOfRoomsValidator = remember {
        FieldValidator { value ->
            ValidationHelper.validateSelection(value, "Number of rooms Validator")
        }
    }

    LaunchedEffect(Unit){
        viewModel.findNumberOfRooms()
        if (selectedTime == null) {
            // Restore from persisted data or use default
            val timeToSet = persistedFormData.cleaningTime.ifEmpty { times[1] }
            viewModel.setSelectedTime(timeToSet)
        }
    }

     when(val result = basicCleaningFormState){
         is BasicCleaningFormState.Success -> {
             LaunchedEffect(result) {
                 goToPreview.invoke(NavigationUtils.encodeObject(persistedFormData),NavigationUtils.encodeObject(result.data))
             }
         }
         is BasicCleaningFormState.Error -> {
             LaunchedEffect(result) {
                 snackBar.showError(result.message)
                 viewModel.resetState()
             }

         }
         is BasicCleaningFormState.Loading -> {
             LoadingDialog(true)
         }
         is BasicCleaningFormState.Idle -> {
             LoadingDialog(false)

         }
     }



    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column {
            // Fixed Header
            FixedHeaderWithBackButton(
                title = "Basic Cleaning",
                onBackClick = goBack
            )

            // Scrollable Content
            Column(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 100.dp) // Space for bottom button
            ) {
                Spacer(modifier = modifier.height(22.dp))
                Text(
                    text = "Please fill in the form below with the required information",
                    style = getPoppinsRegular14(), color = Color(0xFF252525)
                )
                Spacer(modifier = modifier.height(22.dp))
                ValidatedDropdownField(
                    labelText = "Number of rooms",
                    placeHolder = "Select number of rooms",
                    fieldValidator = numberOfRoomsValidator,
                    defaultText = persistedFormData.numberOfRooms?.first?:"",
                    options = numberOfRoomsModelData.map { it.name },
                    onSelectionChange = { selectedRoomNo ->
                        numberOfRoomsId =
                            numberOfRoomsModelData.find { it.name == selectedRoomNo }?.id ?: 0

                    },
                    isLoading = isNumberOfRoomsLoading,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Select Time Slot",
                    style = getPoppinsRegular14()
                )
                Spacer(modifier = modifier.height(12.dp))
                TimeSlotGrid(times, selectedTime) { viewModel.setSelectedTime(it) }
                Spacer(modifier = Modifier.height(15.dp))
                InfoBanner("Kindly note that you are to select between 1 to 6 days within a week excluding sunday.")
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Cleaning Date(s)",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    style = getPoppinsRegular14()
                )
                Spacer(modifier = Modifier.height(12.dp))
                MonthlyCalendarRangeSelector(
                    dateRange = dateRange,
                    onDateRangeChanged = { dateRange = it },
                    minRequiredDays = 1,
                    maxAllowedDays = 6,
                    excludeSundays = true
                )
            }
        }

        PrimaryButton("Continue", {
            val isFormValid = numberOfRoomsValidator.forceValidation()
            val selectedDates: Set<String> = dateRange.getFormattedDates(excludeSundays = true)

            if(isFormValid && selectedDates.isNotEmpty()) {
                val model = BasicCleaningFormModel(
                    numberOfRooms = Pair(numberOfRoomsValidator.value.value,numberOfRoomsId.toString()),
                    cleaningTime = selectedTime?:"",
                    cleaningDate = selectedDates.toList()
                )
                viewModel.saveFormData(model)
                viewModel.getBasicCleaningBreakdown(numberOfRoomsId,selectedTime?.formatTime()?:"",selectedDates.toList())
            }else{
                snackBar.showError("Please fill in the required fields")
            }
        }, modifier = Modifier.align(BottomCenter).padding(20.dp))

        // Snackbar at bottom
        CustomSnackbarHost(
            snackbarHostState = snackBarHostState,
            modifier = Modifier.align(BottomCenter).padding(20.dp)
        )
    }
}
