package com.exquisite.a_mobile_kmm.feature.septic.presenter.septic_commercial_form

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.collectAsState
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
import com.exquisite.a_mobile_kmm.core.nav.NavigationUtils
import com.exquisite.a_mobile_kmm.core.screenUtils.FieldValidator
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationHelper
import com.exquisite.a_mobile_kmm.core.screen_components.DateModel
import com.exquisite.a_mobile_kmm.core.screen_components.FixedHeaderWithBackButton
import com.exquisite.a_mobile_kmm.core.screen_components.GenericTextArea
import com.exquisite.a_mobile_kmm.core.screen_components.HybridDatePicker
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedDropdownField
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedTextField
import com.exquisite.a_mobile_kmm.core.screen_components.generateAvailableDates
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.feature.septic.domain.model.SepticTruckSizeModel
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.LoadingDialog
import com.exquisite.dripp.core.components.rememberSnackBar
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SepticCommercialFormScreen(
    goBack: () -> Unit,
    goToSuccess: (String, String, String) -> Unit,
    viewModel: SepticCommercialFormViewModel = koinViewModel<SepticCommercialFormViewModel>(),
    modifier: Modifier = Modifier
) {
    val (snackBar, snackBarHostState) = rememberSnackBar()

    val state by viewModel.septicCommercialFormState.collectAsState()
    val septicTruckSizeState by viewModel.septicTruckSizeState.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    var truckSize by remember { mutableStateOf<List<SepticTruckSizeModel>>(emptyList()) }
    var selectedTruckSize by remember { mutableStateOf<SepticTruckSizeModel?>(null) }
    val availableQuickDates = remember { generateAvailableDates(11) }
    var additionalMessage by remember{mutableStateOf("")}
    when (val result = septicTruckSizeState) {
        is SepticTruckSizeState.Idle -> {
            isLoading = false
        }

        is SepticTruckSizeState.Loading -> {
            isLoading = true
        }

        is SepticTruckSizeState.GetTruckSizeSuccess -> {
            isLoading = false
            truckSize = result.data
        }

        is SepticTruckSizeState.Error -> {
            isLoading = false
            snackBar.showError(result.message)
        }
    }

    when (state) {
        is SepticCommercialFormState.Idle -> {
            LoadingDialog(false)

        }

        is SepticCommercialFormState.Loading -> {
            LoadingDialog(true)
        }

        is SepticCommercialFormState.SendEnquirySuccess -> {
            viewModel.clearError()
            goToSuccess.invoke(
                "Successful!✅",
                "Thank you for reaching out to us. We will get in touch with you as soon as possible.",
                "Done"
            )
        }

        is SepticCommercialFormState.Error -> {
            // Show error message
        }
    }

    val validateBusinessName = remember {
        FieldValidator(
            ValidationHelper::validateCompanyName
        )
    }

    val emailValidator = remember {
        FieldValidator(
            ValidationHelper::validateEmail
        )
    }


    val recipientNameValidator = remember {
        FieldValidator(
            ValidationHelper::validateName
        )
    }

    val recipientPhoneValidator = remember {
        FieldValidator(
            ValidationHelper::validatePhoneNumber
        )
    }

    val truckSizeValidator = remember {
        FieldValidator { value ->
            ValidationHelper.validateSelection(value, "Number of rooms Validator")
        }
    }

    var availableExecutionDate by remember { mutableStateOf<DateModel?>(null) }
    val isFutureDate =
        availableExecutionDate != null && !availableQuickDates.any { it.fullDate == availableExecutionDate?.fullDate }
    var showModalCalendar by remember { mutableStateOf(false) }

    // Initialize default values if not set
    LaunchedEffect(Unit) {

        if (availableExecutionDate == null) {
            availableExecutionDate = availableQuickDates.firstOrNull()
        }
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
                title = "Commercial Pest Control",
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
                    labelText = "Business Name",
                    placeHolder = "Enter business name",
                    fieldValidator = validateBusinessName,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = Modifier.height(15.dp))
                ValidatedTextField(
                    labelText = "Business Email",
                    placeHolder = "Enter business email ",
                    fieldValidator = emailValidator,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = Modifier.height(15.dp))
                ValidatedTextField(
                    labelText = "Recipient Name",
                    placeHolder = "Enter Recipient Name",
                    fieldValidator = recipientNameValidator,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = modifier.height(15.dp))
                ValidatedTextField(
                    labelText = "Recipient Phone Number",
                    placeHolder = "Enter Recipient Phone Number ",
                    fieldValidator = recipientPhoneValidator,
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                )
                Spacer(modifier = modifier.height(15.dp))
                ValidatedDropdownField(
                    labelText = "Truck Size in Liters",
                    placeHolder = "Select truck size",
                    fieldValidator = truckSizeValidator,
                    options = truckSize.map { it.liter.toString() },
                    onSelectionChange = { selectedRoomName ->
                        selectedTruckSize =
                            truckSize.find { it.liter.toString() == selectedRoomName }
                    },
                    isLoading = isLoading,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = modifier.height(15.dp))
                HybridDatePicker(
                    dates = availableQuickDates,
                    selectedDate = availableExecutionDate,
                    onDateSelected = {
                        availableExecutionDate = it
                    },
                    onOpenFullCalendar = {
                        showModalCalendar = true
                    }
                )
                // 4. Show a summary if a "Future Date" was picked that isn't in the slider
                if (isFutureDate) {
                    SuggestionChip(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                        onClick = {
                            showModalCalendar = true
                        },
                        label = {
                            Text(
                                text = "Selected: ${availableExecutionDate?.fullDate} (Change)",
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
                Spacer(modifier = modifier.height(15.dp))
                GenericTextArea(
                    value = additionalMessage,
                    onValueChange = { additionalMessage = it },
                    labelText = "Additional Message (Optional)",
                    placeHolder = "Enter Additional Message",
                    minLines = 4,
                    maxLines = 6,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
                Spacer(modifier = modifier.height(25.dp))
            }
        }

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
                            availableExecutionDate = newDate
                        }
                        showModalCalendar = false
                    }) { Text("Select") }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        PrimaryButton("Continue", {
            val isValidatedBusiness = validateBusinessName.forceValidation()
            val isValidatedEmail = emailValidator.forceValidation()
            val isValidatedRecipientName = recipientNameValidator.forceValidation()
            val isValidatedRecipientPhone = recipientPhoneValidator.forceValidation()
            val isValidatedTruckSize = truckSizeValidator.forceValidation()


            if (isValidatedBusiness && isValidatedEmail && isValidatedRecipientName && isValidatedRecipientPhone && isValidatedTruckSize) {
            viewModel.sendEnquiry(validateBusinessName.value.value
                ,recipientNameValidator.value.value,recipientPhoneValidator.value.value,
                emailValidator.value.value,selectedTruckSize?.liter.toString(),availableExecutionDate?.fullDate.toString(),"")

            } else {
                snackBar.showError("Kindly fill all required fields in the form")
            }
        }, modifier = Modifier.align(BottomCenter).padding(20.dp))

        // Snackbar at bottom
        CustomSnackbarHost(
            snackbarHostState = snackBarHostState,
            modifier = Modifier.align(BottomCenter).padding(20.dp)
        )
    }
}
