package com.exquisite.a_mobile_kmm.feature.pest_control.presenter.pest_control_residential_form_two

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.email_icon
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exquisite.a_mobile_kmm.core.camera.rememberCameraLauncher
import com.exquisite.a_mobile_kmm.core.nav.NavigationUtils
import com.exquisite.a_mobile_kmm.core.screenUtils.FieldValidator
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationHelper
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationResult
import com.exquisite.a_mobile_kmm.core.screenUtils.generateImageFileName
import com.exquisite.a_mobile_kmm.core.screen_components.DateModel
import com.exquisite.a_mobile_kmm.core.screen_components.EmptyState
import com.exquisite.a_mobile_kmm.core.screen_components.FixedHeaderWithBackButton
import com.exquisite.a_mobile_kmm.core.screen_components.HybridDatePicker
import com.exquisite.a_mobile_kmm.core.screen_components.ImageGrid
import com.exquisite.a_mobile_kmm.core.screen_components.MediaSourceDialog
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.screen_components.RadioButton
import com.exquisite.a_mobile_kmm.core.screen_components.TimeSlotGrid
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedDropdownField
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedTextField
import com.exquisite.a_mobile_kmm.core.screen_components.generateAvailableDates
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.feature.auth.presenter.upload_image.ImageUploadState
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.domain.model.DocumentType
import com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.deep_cleaning_form_two.PhotoUploadSection
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.LoadingDialog
import com.exquisite.dripp.core.components.rememberSnackBar
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResidentialPestControlFormTwoScreen(
    viewModel: ResidentialPestControlForm2ViewModel = koinViewModel<ResidentialPestControlForm2ViewModel>(),
    goBack: () -> Unit, goToNextPage: (String) -> Unit, modifier: Modifier = Modifier
) {

    var imageByte by remember { mutableStateOf<ByteArray?>(null) }
    val scope = rememberCoroutineScope()
    val availableQuickDates = remember { generateAvailableDates(11) }

    val formState by viewModel.formState.collectAsStateWithLifecycle()

    val isInspectionFutureDate = formState.inspectionDate != null && !availableQuickDates.any { it.fullDate == formState.inspectionDate?.fullDate }
    val isServiceFutureDate = formState.serviceDate != null && !availableQuickDates.any { it.fullDate == formState.serviceDate?.fullDate }

    var showModalCalendar by remember { mutableStateOf(false) }
    var pickingDateFor by remember { mutableStateOf<String?>(null) } // "INSPECTION" or "SERVICE"

    val imageUploadState = viewModel.imageUploadState.collectAsStateWithLifecycle()

    val times = listOf("9:00 AM", "10:00 AM", "11:30 AM", "12:00 PM", "1:00 PM", "2:30 PM", "4:00 PM")
    val vehicleList = listOf("1", "2", "3", "4", "5")

    val addressValidator = remember {
        FieldValidator(ValidationHelper::validateAddress).apply {
            if (formState.address.isNotEmpty()) setValue(formState.address)
        }
    }

    val numberOfVehiclesValidator = remember {
        FieldValidator { value ->
            ValidationHelper.validateSelection(value, "Number of vehicles ")
        }.apply {
            if (formState.numberOfVehicles.isNotEmpty()) setValue(formState.numberOfVehicles)
        }
    }

    val extraNoteValidator = remember {
        FieldValidator { ValidationResult(true, "") }
    }

    // Sync validators with ViewModel state
    LaunchedEffect(addressValidator.value.value) {
        viewModel.setAddress(addressValidator.value.value)
    }
    LaunchedEffect(numberOfVehiclesValidator.value.value) {
        viewModel.setNumberOfVehicles(numberOfVehiclesValidator.value.value)
    }
    LaunchedEffect(extraNoteValidator.value.value) {
        viewModel.setExtraNote(extraNoteValidator.value.value)
    }

    // Initialize default values if not set
    LaunchedEffect(Unit) {
        if (formState.inspectionTime == null) {
            viewModel.setInspectionTime(times[1])
        }
        if (formState.inspectionDate == null) {
            viewModel.setInspectionDate(availableQuickDates.firstOrNull())
        }
        if (formState.serviceTime == null) {
            viewModel.setServiceTime(times[2])
        }
        if (formState.serviceDate == null) {
            viewModel.setServiceDate(availableQuickDates.firstOrNull())
        }
    }

    val cameraLauncher = rememberCameraLauncher { imageData ->
        imageData?.let {
            imageByte = it
            viewModel.uploadImage(it, generateImageFileName(it))
        }
    }

    val imagePickerLaunch = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let { imageData ->
                imageByte = imageData
                viewModel.uploadImage(imageData, generateImageFileName(imageData))
            }
        }
    )

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

    val (snackBar, snackBarHostState) = rememberSnackBar()
    var showImageSourceDialog by remember { mutableStateOf(false) }

    when (val result = imageUploadState.value) {
        is ImageUploadState.Success -> {
            viewModel.addImageUrl(result.url)
        }
        is ImageUploadState.Loading -> {
            LoadingDialog(true)
        }
        is ImageUploadState.Error -> {
            snackBar.showError("Error: ${result.message}")
        }
        is ImageUploadState.Idle -> {
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
                title = "Residential Pest Control",
                onBackClick = goBack
            )

            // Scrollable Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
            ) {
                Spacer(modifier = modifier.height(22.dp))
                Text(
                    text = "Please fill in the form below with the required information",
                    style = getPoppinsRegular14(), color = Color(0xFF252525)
                )
                Spacer(modifier = modifier.height(15.dp))
                ValidatedTextField(
                    labelText = "Address",
                    placeHolder = "Enter your address",
                    fieldValidator = addressValidator,
                    defaultText = formState.address,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    leadingIconRes = Res.drawable.email_icon
                )
                Spacer(modifier = modifier.height(15.dp))

                PhotoUploadSection(
                    title = "Photos of space to be serviced",
                    ctaText = "Tap to Capture or Upload",
                    helperText = "Max 5 photos • High quality preferred",
                    onTap = {
                        showImageSourceDialog = true
                    },
                )
                Spacer(modifier = modifier.height(15.dp))
                if (formState.images.isEmpty()) {
                    EmptyState(
                        "No Image!",
                        "Your images will be displayed here",
                        modifier = Modifier.padding(24.dp)
                    ) // TODO: add the image icon here
                } else {
                    Column(modifier = modifier.padding(24.dp)) {
                        ImageGrid(formState.images, deleteImage = { image ->
                            viewModel.removeImageUrl(image)
                        })
                    }
                }

                // Inspection
                Text(
                    text = "Select Inspection Date",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    style = getPoppinsRegular14()
                )
                Spacer(modifier = modifier.height(12.dp))
                HybridDatePicker(
                    dates = availableQuickDates,
                    selectedDate = formState.inspectionDate,
                    onDateSelected = { viewModel.setInspectionDate(it) },
                    onOpenFullCalendar = {
                        pickingDateFor = "INSPECTION"
                        showModalCalendar = true
                    }
                )
                // 4. Show a summary if a "Future Date" was picked that isn't in the slider
                if (isInspectionFutureDate) {
                    SuggestionChip(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                        onClick = {
                            pickingDateFor = "INSPECTION"
                            showModalCalendar = true
                        },
                        label = {
                            Text(
                                text = "Selected: ${formState.inspectionDate?.fullDate} (Change)",
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
                    text = "Select Inspection Time",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    style = getPoppinsRegular14()
                )
                Spacer(modifier = modifier.height(12.dp))
                TimeSlotGrid(times, formState.inspectionTime) { viewModel.setInspectionTime(it) }
                Spacer(modifier = modifier.height(24.dp))

                // Service
                Text(
                    text = "Select Service Date",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    style = getPoppinsRegular14()
                )
                Spacer(modifier = modifier.height(12.dp))
                HybridDatePicker(
                    dates = availableQuickDates,
                    selectedDate = formState.serviceDate,
                    onDateSelected = { viewModel.setServiceDate(it) },
                    onOpenFullCalendar = {
                        pickingDateFor = "SERVICE"
                        showModalCalendar = true
                    }
                )
                // 4. Show a summary if a "Future Date" was picked that isn't in the slider
                if (isServiceFutureDate) {
                    SuggestionChip(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                        onClick = {
                            pickingDateFor = "SERVICE"
                            showModalCalendar = true
                        },
                        label = {
                            Text(
                                text = "Selected: ${formState.serviceDate?.fullDate} (Change)",
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
                    text = "Select Service Time",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    style = getPoppinsRegular14()
                )
                Spacer(modifier = modifier.height(12.dp))
                TimeSlotGrid(times, formState.serviceTime) { viewModel.setServiceTime(it) }
                Spacer(modifier = modifier.height(24.dp))
                // service time
                ValidatedTextField(
                    labelText = "Extra Note(Optional)",
                    placeHolder = "Eg Rodents mostly in the kitchen",
                    fieldValidator = extraNoteValidator,
                    defaultText = formState.extraNote,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                )
                Spacer(modifier = modifier.height(10.dp))
                RadioButton("Do you have pest in your vehicle?", formState.hasPestInVehicle, { result ->
                    viewModel.setHasPestInVehicle(result)
                })
                Spacer(modifier = modifier.height(15.dp))
                if (formState.hasPestInVehicle) {
                    ValidatedDropdownField(
                        labelText = "Number of vehicles",
                        placeHolder = "Select number of vehicles",
                        fieldValidator = numberOfVehiclesValidator,
                        defaultText = formState.numberOfVehicles,
                        options = vehicleList.map { it },
                        onSelectionChange = { selectedNumberOfVehicles ->
                            viewModel.setNumberOfVehicles(selectedNumberOfVehicles)
                        },
                        isLoading = false,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = modifier.height(10.dp))
                RadioButton("Do you want hot fogging?", formState.wantsHotFogging, { result ->
                    viewModel.setWantsHotFogging(result)
                })
                Spacer(modifier = modifier.height(10.dp))

                PrimaryButton("Continue", {
                  val isValidatedAddress = addressValidator.forceValidation()
                    if(isValidatedAddress &&  formState.images.size >= 5){
                   
                        goToNextPage.invoke(NavigationUtils.encodeObject(formState))
                    }else{
                        snackBar.showError("Kindly upload at least five images before you proceed")
                    }
                }, modifier = Modifier.padding(horizontal = 0.dp))
            }
        }

        // Snackbar at bottom
        CustomSnackbarHost(
            snackbarHostState = snackBarHostState,
            modifier = Modifier.align(BottomCenter).padding(20.dp)
        )
    }

    if (showImageSourceDialog) {
        MediaSourceDialog(
            onDismiss = { showImageSourceDialog = false },
            title = "Upload Picture",
            description = "Choose how you'd like to upload your picture:",
            showCamera = true,
            showGallery = true,
            showDocument = false,
            onCameraSelected = {
                showImageSourceDialog = false
                cameraLauncher.launch()
            },
            onGallerySelected = {
                showImageSourceDialog = false
                imagePickerLaunch.launch()
            }
        )
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
                        if (pickingDateFor == "INSPECTION") {
                            viewModel.setInspectionDate(newDate)
                        } else {
                            viewModel.setServiceDate(newDate)
                        }
                    }
                    showModalCalendar = false
                    pickingDateFor = null
                }) { Text("Select") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}