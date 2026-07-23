package com.exquisite.a_mobile_kmm.feature.janitorial.presenter.janitorial

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
import com.exquisite.a_mobile_kmm.core.screenUtils.FieldValidator
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationHelper
import com.exquisite.a_mobile_kmm.core.screenUtils.formatTime
import com.exquisite.a_mobile_kmm.core.screenUtils.generateImageFileName
import com.exquisite.a_mobile_kmm.core.screen_components.DateModel
import com.exquisite.a_mobile_kmm.core.screen_components.EmptyState
import com.exquisite.a_mobile_kmm.core.screen_components.FixedHeaderWithBackButton
import com.exquisite.a_mobile_kmm.core.screen_components.HybridDatePicker
import com.exquisite.a_mobile_kmm.core.screen_components.ImageGrid
import com.exquisite.a_mobile_kmm.core.screen_components.MediaSourceDialog
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.screen_components.TimeSlotGrid
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedTextField
import com.exquisite.a_mobile_kmm.core.screen_components.generateAvailableDates
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.feature.auth.presenter.upload_image.ImageUploadState
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
fun JanitorialScreen(
    goBack: () -> Unit, goToSuccessPage: (String, String, String) -> Unit,
    viewModel: JanitorialViewModel = koinViewModel<JanitorialViewModel>(),
    modifier: Modifier = Modifier
) {
    val janitorialState = viewModel.janitorialState.collectAsStateWithLifecycle()
    val imageUploadState = viewModel.imageUploadState.collectAsStateWithLifecycle()
    val (snackBar, snackBarHostState) = rememberSnackBar()
    val availableQuickDates = remember { generateAvailableDates(11) }
    var selectedDate by remember { mutableStateOf<DateModel?>(null) }
    var showModalCalendar by remember { mutableStateOf(false) }
    val isFutureDate = selectedDate != null && !availableQuickDates.any { it.fullDate == selectedDate?.fullDate }
    val times =
        listOf("9:00 AM", "10:00 AM", "11:30 AM", "12:00 PM", "1:00 PM", "2:30 PM", "4:00 PM")
    var selectedAvailableTime by remember { mutableStateOf(times[1]) }
    var selectedResumptionTime by remember { mutableStateOf(times[1]) }
    var showImageSourceDialog by remember { mutableStateOf(false) }
    val imageUrl by remember { mutableStateOf<MutableList<String>>(mutableListOf()) }

    var imageByte by remember {mutableStateOf<ByteArray?>(null)}
    val scope = rememberCoroutineScope()


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

    when (val result = janitorialState.value) {
        is JanitorialState.Idle -> {
            LoadingDialog(false)

        }
        is JanitorialState.Loading -> {
            LoadingDialog(true)

        }
        is JanitorialState.Error -> {
            snackBar.showError("Error: ${result.message}")
        }
        is JanitorialState.Success -> {
            goToSuccessPage.invoke(
                "Successful!✅",
                "Thank you for faking this request, our representative will get in touch soon",
                "Done"
            )
        }
    }

    when (val result = imageUploadState.value) {
        is ImageUploadState.Success -> {
            imageUrl.add(result.url)
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

    val companyNameValidator = remember {
        FieldValidator(
            ValidationHelper::companyNameValidator
        )
    }

    LaunchedEffect(Unit) {
        selectedAvailableTime = times[0]
        selectedResumptionTime = times[1]
        selectedDate =availableQuickDates.firstOrNull()

    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column {
            // Fixed Header
            FixedHeaderWithBackButton(
                title = "Janitorial Service",
                onBackClick = goBack
            )

            // Scrollable Content
            Column(
                modifier = modifier
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
                    labelText = "Company Name",
                    placeHolder = "Enter your company name",
                    fieldValidator = companyNameValidator,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = Modifier.height(15.dp))

                ValidatedTextField(
                    labelText = "Company Email",
                    placeHolder = "Enter your company email",
                    fieldValidator = emailValidator,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = Modifier.height(15.dp))
                ValidatedTextField(
                    labelText = "Company Phone",
                    placeHolder = "Enter your company phone ",
                    fieldValidator = phoneValidator,
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = Modifier.height(15.dp))
                ValidatedTextField(
                    labelText = "Company Address",
                    placeHolder = "Enter your company address",
                    fieldValidator = addressValidator,
                    defaultText = "",
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                )

                Spacer(modifier = Modifier.height(15.dp))
                // 3. Implement the Hybrid Picker
                HybridDatePicker(
                    dates = availableQuickDates,
                    selectedDate = selectedDate,
                    onDateSelected = {
                        selectedDate = it
                    },
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
                    text = "Available time",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    style = getPoppinsRegular14()
                )
                Spacer(modifier = modifier.height(12.dp))
                TimeSlotGrid(times, selectedAvailableTime) {
                    selectedAvailableTime = it
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Resumption time",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    style = getPoppinsRegular14()
                )
                Spacer(modifier = modifier.height(12.dp))
                TimeSlotGrid(times, selectedResumptionTime) {
                    selectedResumptionTime = it
                }

                Spacer(modifier = Modifier.height(15.dp))
                PhotoUploadSection(
                    title = "Photos of space to be serviced",
                    ctaText = "Tap to Capture or Upload",
                    helperText = "Max 5 photos • High quality preferred",
                    onTap = {
                        showImageSourceDialog = true
                    },
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                if (imageUrl.isEmpty()) {
                    EmptyState(
                        "No Image!",
                        "Your images will be displayed here",
                        modifier = Modifier.padding(24.dp)
                    ) // TODO: add the image icon here
                } else {
                    Column(modifier = modifier.padding(24.dp)) {
                        ImageGrid(imageUrl, deleteImage = { image ->
                            imageUrl.remove(image)
                        })
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))


            }
        }

        PrimaryButton("Done", {
            val isValidatedAddress = addressValidator.forceValidation()
            val isValidateEmail = emailValidator.forceValidation()
            val isValidatePhone = phoneValidator.forceValidation()
            val isCompanyValid = companyNameValidator.forceValidation()


            if (isValidatedAddress && isValidateEmail && isCompanyValid && isValidatePhone && imageUrl.isNotEmpty()) {
            viewModel.createJanitorial(
                companyName = companyNameValidator.value.value,
                companyEmail = emailValidator.value.value,
                companyAddress = addressValidator.value.value,
                availabilityDate = selectedDate?.fullDate ?: "",
                availabilityTime = selectedAvailableTime.formatTime(),
                resumptionTime = selectedResumptionTime.formatTime(),
                buildingImage = imageUrl,
                phoneNo = phoneValidator.value.value
            )

            } else {
                snackBar.showError("kindly fill in the form with the required details")
            }
        }, modifier = Modifier.align(BottomCenter).padding(20.dp))

        // Snackbar above the button
        CustomSnackbarHost(
            snackbarHostState = snackBarHostState,
            modifier = Modifier.align(BottomCenter).padding(bottom = 90.dp)
        )

    }


    if(showImageSourceDialog){
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
                        selectedDate = newDate
                    }
                    showModalCalendar = false
                }) { Text("Select") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
