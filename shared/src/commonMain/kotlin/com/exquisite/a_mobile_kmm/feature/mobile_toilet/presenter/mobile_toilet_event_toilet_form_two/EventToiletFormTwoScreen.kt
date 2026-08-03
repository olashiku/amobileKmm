package com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.mobile_toilet_event_toilet_form_two

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exquisite.a_mobile_kmm.core.nav.NavigationUtils
import com.exquisite.a_mobile_kmm.core.screenUtils.FieldValidator
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationHelper
import com.exquisite.a_mobile_kmm.core.screenUtils.formatTime
import com.exquisite.a_mobile_kmm.core.screen_components.DateModel
import com.exquisite.a_mobile_kmm.core.screen_components.FixedHeaderWithBackButton
import com.exquisite.a_mobile_kmm.core.screen_components.HybridDatePicker
import com.exquisite.a_mobile_kmm.core.screen_components.InfoNotification
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.screen_components.TimeSlotGrid
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedTextField
import com.exquisite.a_mobile_kmm.core.screen_components.generateAvailableDates
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.MobileToiletEventFormOneFormData
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.ToiletAvailabilityModel
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
fun EventToiletFormTwoScreen(
    mobileToiletEventFormOneFormData: MobileToiletEventFormOneFormData,
    toiletAvailabilityModel: ToiletAvailabilityModel,
    goBack: () -> Unit,
    goToPricePage: (String) -> Unit,
    viewModel: EventToiletFormViewModel = koinViewModel<EventToiletFormViewModel>(),
    modifier: Modifier = Modifier
) {
    val (snackBar, snackBarHostState) = rememberSnackBar()
    val persistedFormData by viewModel.eventToiletFormTwoModel.collectAsStateWithLifecycle()
    var pickingDateFor by remember { mutableStateOf("") }

    val times =
        listOf( "12:00 AM", "12:30 AM",
            "1:00 AM", "1:30 AM",
            "2:00 AM", "2:30 AM",
            "3:00 AM", "3:30 AM",
            "4:00 AM", "4:30 AM",
            "5:00 AM", "5:30 AM",
            "6:00 AM", "6:30 AM",
            "7:00 AM", "7:30 AM",
            "8:00 AM", "8:30 AM",
            "9:00 AM", "9:30 AM",
            "10:00 AM", "10:30 AM",
            "11:00 AM", "11:30 AM",
            "12:00 PM", "12:30 PM",
            "1:00 PM", "1:30 PM",
            "2:00 PM", "2:30 PM",
            "3:00 PM", "3:30 PM",
            "4:00 PM", "4:30 PM",
            "5:00 PM", "5:30 PM",
            "6:00 PM", "6:30 PM",
            "7:00 PM", "7:30 PM",
            "8:00 PM", "8:30 PM",
            "9:00 PM", "9:30 PM",
            "10:00 PM", "10:30 PM",
            "11:00 PM", "11:30 PM")
    val availableQuickDates = remember { generateAvailableDates(11) }

    val isEventStartDate =
        persistedFormData.eventStartDate != null && !availableQuickDates.any { it.fullDate == persistedFormData.eventStartDate?.fullDate }
    val isEventEndDate =
        persistedFormData.eventEndDate != null && !availableQuickDates.any { it.fullDate == persistedFormData.eventEndDate?.fullDate }

    var showModalCalendar by remember { mutableStateOf(false) }

    val eventToiletFormState = viewModel.eventToiletFormState.collectAsStateWithLifecycle()

    when (val result = eventToiletFormState.value) {
        is EventToiletFormState.PriceSuccess -> {
            LaunchedEffect(result.price) {
                goToPricePage.invoke(NavigationUtils.encodeObject(result.price))
                viewModel.clearState()
            }
        }

        is EventToiletFormState.Error -> {
            LaunchedEffect(result.message) {
                snackBar.showError(result.message)
                viewModel.clearState()
            }
        }

        is EventToiletFormState.Loading -> {
            LoadingDialog(true)
        }

        is EventToiletFormState.Idle -> {
            LoadingDialog(false)
        }
    }

    val standardToiletValidator = remember(toiletAvailabilityModel.availableStandardToilets) {
        FieldValidator { quantity ->
            ValidationHelper.validateToiletQuantity(
                quantity = quantity,
                maxAvailable = toiletAvailabilityModel.availableStandardToilets,
                toiletType = "standard toilet"
            )
        }
    }

    val vipToiletValidator = remember(toiletAvailabilityModel.availableVipToilet) {
        FieldValidator { quantity ->
            ValidationHelper.validateToiletQuantity(
                quantity = quantity,
                maxAvailable = toiletAvailabilityModel.availableVipToilet,
                toiletType = "VIP toilet"
            )
        }
    }

    val numberOfGuestValidator = remember {
        FieldValidator(
            ValidationHelper::validateNumberOfGuest
        )
    }

    // Persist validator values to ViewModel
    LaunchedEffect(numberOfGuestValidator.value.value) {
        viewModel.setNumberOfGuest(numberOfGuestValidator.value.value)
    }

    LaunchedEffect(standardToiletValidator.value.value) {
        viewModel.setNumberOfStandardToilet(standardToiletValidator.value.value)
    }

    LaunchedEffect(vipToiletValidator.value.value) {
        viewModel.setNumberOfVipToilet(vipToiletValidator.value.value)
    }

    LaunchedEffect(Unit) {
        if (persistedFormData.eventStartTime == null) {
            viewModel.setStartTime(times[0])
        }

        if (persistedFormData.eventEndTime == null) {
            viewModel.setEndTime(times[1])
        }
        if (persistedFormData.eventStartDate == null) {
            viewModel.setStartDate(availableQuickDates.firstOrNull())
        }

        if (persistedFormData.eventEndDate == null) {
            viewModel.setEndDate(availableQuickDates[1])
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
                title = "${mobileToiletEventFormOneFormData.selectedCleaningOption.replaceFirstChar { it.uppercase() }} Toilet Request",
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
                Spacer(modifier = modifier.height(10.dp))
                InfoNotification("Currently you have ${toiletAvailabilityModel.availableStandardToilets} Standard toilet(s) and ${toiletAvailabilityModel.availableVipToilet}  VIP toilet(s) at your disposal ")

                Spacer(modifier = modifier.height(10.dp))
                ValidatedTextField(
                    labelText = "Expected Number of Guests for the Event",
                    placeHolder = "Enter number of guests",
                    fieldValidator = numberOfGuestValidator,
                    defaultText = persistedFormData.numberOfGuest,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                )
                when (mobileToiletEventFormOneFormData.selectedCleaningOption) {
                    "standard" -> {
                        Spacer(modifier = modifier.height(10.dp))
                        ValidatedTextField(
                            labelText = "Number of Standard Toilet",
                            placeHolder = "Enter number of standard toilets",
                            fieldValidator = standardToiletValidator,
                            defaultText = persistedFormData.numberOfStandardToilet,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next,
                        )
                    }

                    "vip" -> {
                        Spacer(modifier = modifier.height(10.dp))
                        ValidatedTextField(
                            labelText = "Number of Vip Toilet",
                            placeHolder = "Enter number of vip toilets",
                            fieldValidator = vipToiletValidator,
                            defaultText = persistedFormData.numberOfVipToilet,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next,
                        )
                    }

                    else -> {
                        Spacer(modifier = modifier.height(10.dp))
                        ValidatedTextField(
                            labelText = "Number of Standard Toilet",
                            placeHolder = "Enter number of standard toilets",
                            fieldValidator = standardToiletValidator,
                            defaultText = persistedFormData.numberOfStandardToilet,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next,
                        )
                        Spacer(modifier = modifier.height(10.dp))
                        ValidatedTextField(
                            labelText = "Number of Vip Toilet",
                            placeHolder = "Enter number of vip toilets",
                            fieldValidator = vipToiletValidator,
                            defaultText = persistedFormData.numberOfVipToilet,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done,
                        )
                    }
                }
                Spacer(modifier = modifier.height(10.dp))

                // TODO: start date picker
                Text(
                    text = "Event Start Date",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    style = getPoppinsRegular14()
                )
                Spacer(modifier = modifier.height(12.dp))

                HybridDatePicker(
                    dates = availableQuickDates,
                    selectedDate = persistedFormData.eventStartDate,
                    onDateSelected = {
                        viewModel.setStartDate(it)
                    },
                    onOpenFullCalendar = {
                        pickingDateFor = "START_DATE"
                        showModalCalendar = true
                    }
                )
                // 4. Show a summary if a "Future Date" was picked that isn't in the slider
                if (isEventStartDate) {
                    SuggestionChip(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                        onClick = {
                            pickingDateFor = "START_DATE"
                            showModalCalendar = true
                        },
                        label = {
                            Text(
                                text = "Selected: ${persistedFormData.eventStartDate?.fullDate} (Change)",
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

                Text(
                    text = "Select Start Time",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    style = getPoppinsRegular14()
                )
                Spacer(modifier = modifier.height(12.dp))
                TimeSlotGrid(times, persistedFormData.eventStartTime) {
                    viewModel.setStartTime(it)
                }
                Spacer(modifier = modifier.height(24.dp))

                // TODO: end date picker
                Text(
                    text = "Event End Date",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    style = getPoppinsRegular14()
                )
                Spacer(modifier = modifier.height(12.dp))

                HybridDatePicker(
                    dates = availableQuickDates,
                    selectedDate = persistedFormData.eventEndDate,
                    onDateSelected = {
                        viewModel.setEndDate(it)
                    },
                    onOpenFullCalendar = {
                        pickingDateFor = "END_DATE"
                        showModalCalendar = true
                    }
                )
                // 4. Show a summary if a "Future Date" was picked that isn't in the slider
                if (isEventEndDate) {
                    SuggestionChip(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                        onClick = {
                            pickingDateFor = "END_DATE"
                            showModalCalendar = true
                        },
                        label = {
                            Text(
                                text = "Selected: ${persistedFormData.eventEndDate?.fullDate} (Change)",
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

                Text(
                    text = "Select End Time",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    style = getPoppinsRegular14()
                )
                Spacer(modifier = modifier.height(12.dp))
                TimeSlotGrid(times, persistedFormData.eventEndTime) {
                    viewModel.setEndTime(it)
                }
                Spacer(modifier = modifier.height(24.dp))
            }
        }


        PrimaryButton("Continue", {

            val endDate =
                kotlinx.datetime.LocalDate.parse(persistedFormData.eventEndDate?.fullDate ?: "")
            val startDate =
                kotlinx.datetime.LocalDate.parse(persistedFormData.eventStartDate?.fullDate ?: "")
            if (endDate < startDate) {
                snackBar.showError("End date cannot be before start date")
            } else {
                when (mobileToiletEventFormOneFormData.selectedCleaningOption) {
                    "standard" -> {
                        val isValidStandardToilet = standardToiletValidator.forceValidation()
                        val isValidNoOfGuest = numberOfGuestValidator.forceValidation()

                        if (isValidStandardToilet && isValidNoOfGuest) {

                            viewModel.getToiletPrice(
                                numberOfGuestValidator.value.value,
                                mobileToiletEventFormOneFormData.selectedCleaningOption.uppercase(),
                                standardToiletValidator.value.value,
                                "0",
                                persistedFormData.eventStartDate?.fullDate ?: "",
                                persistedFormData.eventEndDate?.fullDate ?: "",
                                persistedFormData.eventStartTime?.formatTime() ?: "",
                                persistedFormData.eventEndTime?.formatTime() ?: ""
                            )
                        }
                    }

                    "vip" -> {
                        val isValidNoOfGuest = numberOfGuestValidator.forceValidation()
                        val isValidVipToilet = vipToiletValidator.forceValidation()

                        if (isValidVipToilet && isValidNoOfGuest) {
                            viewModel.getToiletPrice(
                                numberOfGuestValidator.value.value,
                                mobileToiletEventFormOneFormData.selectedCleaningOption.uppercase(),
                                "0",
                                vipToiletValidator.value.value,
                                persistedFormData.eventStartDate?.fullDate ?: "",
                                persistedFormData.eventEndDate?.fullDate ?: "",
                                persistedFormData.eventStartTime?.formatTime() ?: "",
                                persistedFormData.eventEndTime?.formatTime() ?: ""
                            )
                        }
                    }
                    else -> {
                        val isValidStandardToilet = standardToiletValidator.forceValidation()
                        val isValidVipToilet = vipToiletValidator.forceValidation()
                        val isValidNoOfGuest = numberOfGuestValidator.forceValidation()

                        if (isValidVipToilet && isValidNoOfGuest && isValidStandardToilet) {

                            viewModel.getToiletPrice(
                                numberOfGuestValidator.value.value,
                                mobileToiletEventFormOneFormData.selectedCleaningOption.uppercase(),
                                standardToiletValidator.value.value,
                                vipToiletValidator.value.value,
                                persistedFormData.eventStartDate?.fullDate ?: "",
                                persistedFormData.eventEndDate?.fullDate ?: "",
                                persistedFormData.eventStartTime?.formatTime() ?: "",
                                persistedFormData.eventEndTime?.formatTime() ?: ""
                            )
                        }
                    }
                }
            }
        }, modifier = Modifier.align(Alignment.BottomCenter).padding(20.dp))


        // Snackbar at bottom
        CustomSnackbarHost(
            snackbarHostState = snackBarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }

    // 5. The Standard Material 3 Date Picker Dialog
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

                        if (pickingDateFor.equals("START_DATE")) {
                            viewModel.setStartDate(newDate)
                        } else {
                            viewModel.setEndDate(newDate)
                        }
                    }
                    showModalCalendar = false
                }) { Text("Select") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
