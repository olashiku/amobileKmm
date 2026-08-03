package com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.mobile_toilet_event_form_three

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exquisite.a_mobile_kmm.core.camera.rememberCameraLauncher
import com.exquisite.a_mobile_kmm.core.screenUtils.FieldValidator
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationHelper
import com.exquisite.a_mobile_kmm.core.screenUtils.generateImageFileName
import com.exquisite.a_mobile_kmm.core.screen_components.EmptyState
import com.exquisite.a_mobile_kmm.core.screen_components.FixedHeaderWithBackButton
import com.exquisite.a_mobile_kmm.core.screen_components.GenericTextArea
import com.exquisite.a_mobile_kmm.core.screen_components.ImageGrid
import com.exquisite.a_mobile_kmm.core.screen_components.MediaSourceDialog
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedDropdownField
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedTextField
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.feature.auth.presenter.upload_image.ImageUploadState
import com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.deep_cleaning_form_two.PhotoUploadSection
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.LoadingDialog
import com.exquisite.dripp.core.components.rememberSnackBar
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MobileToiletFormThreeScreen(
    goBack: () -> Unit,
    goToNextPage: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MobileToiletFormThreeViewModel = koinViewModel<MobileToiletFormThreeViewModel>()
) {
    val (snackBar, snackBarHostState) = rememberSnackBar()
    val persistedFormData by viewModel.formModel.collectAsStateWithLifecycle()
    val imageUploadState by viewModel.imageUploadState.collectAsStateWithLifecycle()

    var imageByte by remember { mutableStateOf<ByteArray?>(null) }
    var imageType by remember { mutableStateOf("") }
    var additionalMessage by remember { mutableStateOf(persistedFormData.additionalMessage) }

    val scope = rememberCoroutineScope()

    // Handle image upload states
    LaunchedEffect(imageUploadState) {
        when (val state = imageUploadState) {
            is ImageUploadState.Success -> {
                snackBar.showSuccess("Image uploaded successfully")
                viewModel.clearImageUploadState()
            }
            is ImageUploadState.Error -> {
                snackBar.showError(state.message)
                viewModel.clearImageUploadState()
            }
            is ImageUploadState.Loading -> {
                // Loading state handled by LoadingDialog below
            }
            is ImageUploadState.Idle -> {
                // Do nothing
            }
        }
    }

    // Persist additional message
    LaunchedEffect(additionalMessage) {
        viewModel.setAdditionalMessage(additionalMessage)
    }


    val cameraLauncher = rememberCameraLauncher { imageData ->
        imageData?.let {
            imageByte = it
            viewModel.setCurrentImageType(imageType)
            viewModel.uploadImage(it, generateImageFileName(it))
        }
    }

    val imagePickerLaunch = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let { imageData ->
                imageByte = imageData
                viewModel.setCurrentImageType(imageType)
                viewModel.uploadImage(imageData, generateImageFileName(imageData))
            }
        }
    )

    var showImageSourceDialog by remember { mutableStateOf(false) }

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

    val companyEmailValidator = remember {
        FieldValidator(
            ValidationHelper::validateEmail
        )
    }

    val phoneValidator = remember {
        FieldValidator(
            ValidationHelper::validatePhoneNumber
        )
    }

    val eventTypeValidator = remember {
        FieldValidator { value ->
            ValidationHelper.validateSelection(value, "Event type")
        }
    }

    val companyNameValidator = remember {
        FieldValidator(
            ValidationHelper::companyNameValidator
        )
    }

    // Persist validator values to ViewModel
    LaunchedEffect(companyNameValidator.value.value) {
        viewModel.setCompanyName(companyNameValidator.value.value)
    }

    LaunchedEffect(fullNameValidator.value.value) {
        viewModel.setContactName(fullNameValidator.value.value)
    }

    LaunchedEffect(addressValidator.value.value) {
        viewModel.setAddress(addressValidator.value.value)
    }

    LaunchedEffect(emailValidator.value.value) {
        viewModel.setContactEmail(emailValidator.value.value)
    }

    LaunchedEffect(phoneValidator.value.value) {
        viewModel.setContactPhone(phoneValidator.value.value)
    }

    LaunchedEffect(eventTypeValidator.value.value) {
        viewModel.setEventType(eventTypeValidator.value.value)
    }

    LaunchedEffect(companyEmailValidator.value.value) {
        viewModel.setCompanyEmail(companyEmailValidator.value.value)
    }

    val eventType = listOf(
        "Wedding",
        "Birthday",
        "Anniversary",
        "Corporate Event",
        "Conference",
        "Concert",
        "Sports Event",
        "Festival",
        "Exhibition",
        "Workshop",
        "Seminar",
        "Networking Event",
        "Product Launch",
        "Charity Event",
        "Graduation",
        "Reunion",
        "Other"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column {
            // Fixed Header
            FixedHeaderWithBackButton(
                title = "Mobile Toilet",
                onBackClick = goBack
            )

            // Scrollable Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(start = 20.dp, end = 20.dp, bottom = 100.dp)
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
                    defaultText = persistedFormData.companyName,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = Modifier.height(15.dp))

                ValidatedTextField(
                    labelText = "Company Email",
                    placeHolder = "Enter your company email",
                    fieldValidator = companyEmailValidator,
                    defaultText = persistedFormData.companyEmail,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = Modifier.height(15.dp))

                ValidatedTextField(
                    labelText = "Contact Name",
                    placeHolder = "Enter you contact name",
                    fieldValidator = fullNameValidator,
                    defaultText = persistedFormData.contactName,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = Modifier.height(15.dp))

                ValidatedTextField(
                    labelText = "Contact Phone Number",
                    placeHolder = "Enter contact phone number ",
                    fieldValidator = phoneValidator,
                    defaultText = persistedFormData.contactPhone,
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = Modifier.height(15.dp))
                ValidatedTextField(
                    labelText = "Contact Email",
                    placeHolder = "Enter contact email",
                    fieldValidator = emailValidator,
                    defaultText = persistedFormData.contactEmail,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )

                Spacer(modifier = Modifier.height(15.dp))
                ValidatedDropdownField(
                    labelText = "Event Type",
                    placeHolder = "Enter event type",
                    fieldValidator = eventTypeValidator,
                    defaultText = persistedFormData.eventType,
                    options = eventType,
                    onSelectionChange = { }
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
                GenericTextArea(
                    value = persistedFormData.additionalMessage,
                    onValueChange = { additionalMessage = it },
                    labelText = "Additional Message (Optional)",
                    placeHolder = "Enter Additional Message",
                    minLines = 4,
                    maxLines = 6,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
                Spacer(modifier = Modifier.height(15.dp))

                // Event location images
                Spacer(modifier = Modifier.height(15.dp))
                PhotoUploadSection(
                    title = "Photos of event location",
                    ctaText = "Tap to Capture or Upload",
                    helperText = "Max 5 photos • High quality preferred",
                    onTap = {
                        imageType = "event_location"
                        showImageSourceDialog = true
                    },
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                if (persistedFormData.eventLocationImages.isEmpty()) {
                    EmptyState(
                        "No Image!",
                        "Your images will be displayed here",
                        modifier = Modifier.padding(24.dp)
                    )
                } else {
                    Column(modifier = modifier.padding(24.dp)) {
                        ImageGrid(persistedFormData.eventLocationImages, deleteImage = { image ->
                            viewModel.removeEventLocationImage(image)
                        })
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))

                // Toilet placement images
                Spacer(modifier = Modifier.height(15.dp))
                PhotoUploadSection(
                    title = "Photos of toilet placement",
                    ctaText = "Tap to Capture or Upload",
                    helperText = "Max 5 photos • High quality preferred",
                    onTap = {
                        imageType = "toilet_placement"
                        showImageSourceDialog = true
                    },
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                if (persistedFormData.toiletPlacementImages.isEmpty()) {
                    EmptyState(
                        "No Image!",
                        "Your images will be displayed here",
                        modifier = Modifier.padding(24.dp)
                    )
                } else {
                    Column(modifier = modifier.padding(24.dp)) {
                        ImageGrid(persistedFormData.toiletPlacementImages, deleteImage = { image ->
                            viewModel.removeToiletPlacementImage(image)
                        })
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        PrimaryButton("Continue", {
            val isCompanyNameValid = companyNameValidator.forceValidation()
            val isCompanyEmailValid = companyEmailValidator.forceValidation()
            val isContactNameValid = fullNameValidator.forceValidation()
            val isAddressValid = addressValidator.forceValidation()
            val isContactEmailValid = emailValidator.forceValidation()
            val isPhoneValid = phoneValidator.forceValidation()
            val isEventTypeValid = eventTypeValidator.forceValidation()

            // Validate images
            val hasEventLocationImages = persistedFormData.eventLocationImages.isNotEmpty()
            val hasToiletPlacementImages = persistedFormData.toiletPlacementImages.isNotEmpty()

            when {
                !isCompanyNameValid || !isCompanyEmailValid || !isContactNameValid
                        || !isAddressValid || !isContactEmailValid || !isPhoneValid || !isEventTypeValid -> {
                    snackBar.showError("Please fill in all required fields")
                }
                !hasEventLocationImages -> {
                    snackBar.showError("Please upload at least one photo of the event location")
                }
                !hasToiletPlacementImages -> {
                    snackBar.showError("Please upload at least one photo of the toilet placement")
                }
                else -> {
                    // Convert form data to JSON and navigate
                    val formDataJson = viewModel.getFormDataAsJson()
                    goToNextPage(formDataJson)
                }
            }
        }, modifier = Modifier.align(Alignment.BottomCenter).padding(20.dp))

        // Snackbar host
        CustomSnackbarHost(
            snackbarHostState = snackBarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }

    // Loading dialog for image upload
    LoadingDialog(imageUploadState is ImageUploadState.Loading)

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
}