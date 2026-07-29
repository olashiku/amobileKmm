package com.exquisite.a_mobile_kmm.feature.septic.presenter.septic_residential_form2

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exquisite.a_mobile_kmm.core.nav.NavigationUtils
import com.exquisite.a_mobile_kmm.core.screenUtils.FieldValidator
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationHelper
import com.exquisite.a_mobile_kmm.core.screen_components.DateModel
import com.exquisite.a_mobile_kmm.core.screen_components.FixedHeaderWithBackButton
import com.exquisite.a_mobile_kmm.core.screen_components.GenericTextArea
import com.exquisite.a_mobile_kmm.core.screen_components.HybridDatePicker
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.screen_components.TimeSlotGrid
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedTextField
import com.exquisite.a_mobile_kmm.core.screen_components.generateAvailableDates
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.rememberSnackBar
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SepticResidentialForm2Screen(
    goBack: () -> Unit ,
    goToCheckout: (String) -> Unit,
    viewModel: SepticResidentialFormViewModel2 = koinViewModel<SepticResidentialFormViewModel2>(),
    modifier: Modifier = Modifier
) {
    val (snackBar, snackBarHostState) = rememberSnackBar()
    val selectedDate by viewModel.selectedDate.collectAsStateWithLifecycle()
    val times =
        listOf("9:00 AM", "10:00 AM", "11:30 AM", "12:00 PM", "1:00 PM", "2:30 PM", "4:00 PM")
    val availableQuickDates = remember { generateAvailableDates(11) }
    val isFutureDate = selectedDate != null && !availableQuickDates.any { it.fullDate == selectedDate?.fullDate }

    val selectedTime by viewModel.selectedTime.collectAsStateWithLifecycle()
    val persistedFormData by viewModel.persistedFormData.collectAsStateWithLifecycle()
    var showModalCalendar by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (selectedTime == null) {
            viewModel.setSelectedTime(times[1])
        }
        if (selectedDate == null) {
            viewModel.setSelectedDate(availableQuickDates.firstOrNull())
        }
    }

    val fullNameValidator = remember {
        FieldValidator(
            ValidationHelper::validateFullName
        )
    }

    val addressValidator = remember {
        FieldValidator(
            ValidationHelper::validateAddress
        )
    }

    val emailValidator = remember {
        FieldValidator(
            ValidationHelper::validateEmail
        )
    }

    val phoneValidator = remember {
        FieldValidator(
            ValidationHelper::validatePhoneNumber
        )
    }

    LaunchedEffect(fullNameValidator.value.value) {
        viewModel.updateFullName(fullNameValidator.value.value)
    }

    LaunchedEffect(emailValidator.value.value) {
        viewModel.updateEmail(emailValidator.value.value)
    }

    LaunchedEffect(phoneValidator.value.value) {
        viewModel.updatePhone(phoneValidator.value.value)
    }

    LaunchedEffect(addressValidator.value.value) {
        viewModel.updateAddress(addressValidator.value.value)
    }

    // Get current date in milliseconds for validation
    val todayMillis = remember {
        val today = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date
        today.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    }

    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= todayMillis
            }

            override fun isSelectableYear(year: Int): Boolean {
                val currentYear = Clock.System.now()
                    .toLocalDateTime(TimeZone.currentSystemDefault())
                    .year
                return year >= currentYear
            }
        }
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column {
            // Fixed Header
            FixedHeaderWithBackButton(
                title = "Residential Septic Request",
                onBackClick = goBack
            )

            // Scrollable Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(start = 20.dp, end = 20.dp, bottom = 80.dp)
            ) {
                Spacer(modifier = modifier.height(22.dp))
                Text(
                    text = "Please fill in the form below with the required information",
                    style = getPoppinsRegular14(), color = Color(0xFF252525)
                )
                Spacer(modifier = modifier.height(22.dp))
                ValidatedTextField(
                    labelText = "Full Name",
                    placeHolder = "Enter you full name",
                    fieldValidator = fullNameValidator,
                    defaultText = persistedFormData.fullName,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = Modifier.height(15.dp))
                ValidatedTextField(
                    labelText = "Email",
                    placeHolder = "Enter email ",
                    fieldValidator = emailValidator,
                    defaultText = persistedFormData.email,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = Modifier.height(15.dp))
                ValidatedTextField(
                    labelText = "Mobile number",
                    placeHolder = "Enter mobile number ",
                    fieldValidator = phoneValidator,
                    defaultText = persistedFormData.phone,
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                )
                Spacer(modifier = Modifier.height(15.dp))
                ValidatedTextField(
                    labelText = "Address",
                    placeHolder = "Enter you address ",
                    fieldValidator = addressValidator,
                    defaultText = persistedFormData.address,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Select Excavation Date",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    style = getPoppinsRegular14()
                )
                Spacer(modifier = modifier.height(12.dp))

                // 3. Implement the Hybrid Picker
                HybridDatePicker(
                    dates = availableQuickDates,
                    selectedDate = selectedDate,
                    onDateSelected = { viewModel.setSelectedDate(it) },
                    onOpenFullCalendar = { showModalCalendar = true }
                )
                // 4. Show a summary if a "Future Date" was picked that isn't in the slider
                if (isFutureDate) {
                    SuggestionChip(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                        onClick = { showModalCalendar = true },
                        label = {
                            Text(
                                text = "Selected: ${selectedDate?.fullDate} (Change)",
                                style = getPoppinsMedium14()
                            )
                        },
                        icon = {
                            Icon(
                                Icons.Default.Edit,
                                null,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Select Excavation Time",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    style = getPoppinsRegular14()
                )
                Spacer(modifier = modifier.height(12.dp))
                TimeSlotGrid(times, selectedTime) { viewModel.setSelectedTime(it) }

                Spacer(modifier = modifier.height(15.dp))
                GenericTextArea(
                    value = persistedFormData.additionalMessage,
                    onValueChange = { viewModel.updateAdditionalMessage(it) },
                    labelText = "Additional Message (Optional)",
                    placeHolder = "Enter Additional Message",
                    minLines = 4,
                    maxLines = 6,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
                Spacer(modifier = modifier.height(15.dp))
            }
        }

        PrimaryButton("Continue", {
            val isNameValid = fullNameValidator.forceValidation()
            val isEmailValid = emailValidator.forceValidation()
            val isPhoneValid = phoneValidator.forceValidation()
            val isAddressValid = addressValidator.forceValidation()
            if(isNameValid && isEmailValid && isPhoneValid && isAddressValid){
                goToCheckout(NavigationUtils.encodeObject(persistedFormData))
            }


        }, modifier = Modifier.align(BottomCenter).padding(20.dp))


        // Snackbar at bottom
        CustomSnackbarHost(
            snackbarHostState = snackBarHostState,
            modifier = Modifier.align(BottomCenter).padding(20.dp)
        )
        if (showModalCalendar) {
            DatePickerDialog(
                onDismissRequest = { showModalCalendar = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            // Convert millis to LocalDate using kotlinx-datetime
                            val instant = Instant.fromEpochMilliseconds(millis)
                            val localDate =
                                instant.toLocalDateTime(TimeZone.currentSystemDefault()).date

                            // Create DateModel from LocalDate
                            val newDate = DateModel(
                                dayName = localDate.dayOfWeek.name.take(3).uppercase(),
                                dayNumber = localDate.dayOfMonth.toString(),
                                fullDate = "${localDate.year}-${
                                    localDate.monthNumber.toString().padStart(2, '0')
                                }-${localDate.dayOfMonth.toString().padStart(2, '0')}"
                            )
                            viewModel.setSelectedDate(newDate)
                        }
                        showModalCalendar = false
                    }) { Text("Select") }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }


}
