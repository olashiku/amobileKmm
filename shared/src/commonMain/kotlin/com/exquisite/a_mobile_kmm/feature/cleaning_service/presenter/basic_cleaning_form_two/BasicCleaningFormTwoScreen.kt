package com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.basic_cleaning_form_two

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
import com.exquisite.a_mobile_kmm.core.screenUtils.generateImageFileName
import com.exquisite.a_mobile_kmm.core.screen_components.EmptyState
import com.exquisite.a_mobile_kmm.core.screen_components.FixedHeaderWithBackButton
import com.exquisite.a_mobile_kmm.core.screen_components.ImageGrid
import com.exquisite.a_mobile_kmm.core.screen_components.MediaSourceDialog
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedDropdownField
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedTextField
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.BasicCleaningBreakdownModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.BasicCleaningFormModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.deep_cleaning_form_two.PhotoUploadSection
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.rememberSnackBar
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BasicCleaningFormTwoScreen(
    basicCleaningFormModel: BasicCleaningFormModel,
    basicCleaningBreakdownModel: BasicCleaningBreakdownModel,
    viewModel: BasicCleaningFormTwoViewModel = koinViewModel<BasicCleaningFormTwoViewModel>(),
    goBack: () -> Unit, goToCheckoutPage: (String) -> Unit, modifier: Modifier = Modifier,
) {
    val (snackBar, snackBarHostState) = rememberSnackBar()

    val isRegionLoading by viewModel.isRegionLoading.collectAsStateWithLifecycle()
    val isLocationLoading by viewModel.isLocationLoading.collectAsStateWithLifecycle()
    val isApartmentTypeLoading by viewModel.isApartmentTypeLoading.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

    val persistedFormData by viewModel.persistedFormData.collectAsStateWithLifecycle()

    val regionData by viewModel.regions.collectAsStateWithLifecycle()
    val locationData by viewModel.locations.collectAsStateWithLifecycle()
    val apartmentTypeData by viewModel.apartmentTypes.collectAsStateWithLifecycle()

    var showImageSourceDialog by remember { mutableStateOf(false) }

    val imageUrl by viewModel.uploadedImageUrls.collectAsStateWithLifecycle()

    var regionId by remember { mutableStateOf(persistedFormData.region?.second?.toInt() ?: 0) }
    var locationId by remember { mutableStateOf(persistedFormData.location?.second?.toInt() ?: 0) }
    var apartmentId by remember {
        mutableStateOf(
            persistedFormData.typeOfApartment?.second?.toInt() ?: 0
        )
    }

    var imageByte by remember { mutableStateOf<ByteArray?>(null) }
    val scope = rememberCoroutineScope()
    val imageUploadState = viewModel.imageUploadState.collectAsStateWithLifecycle()


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


    LaunchedEffect(Unit) {
        viewModel.callAllEndpoints()
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            snackBar.showError(message)
            viewModel.clearError()
        }
    }

    val regionValidator = remember {
        FieldValidator { value ->
            ValidationHelper.validateSelection(value, "Region Validator")
        }
    }

    val locationValidator = remember {
        FieldValidator { value ->
            ValidationHelper.validateSelection(value, "Location Validator")
        }
    }
    val typeOfApartmentValidator = remember {
        FieldValidator { value ->
            ValidationHelper.validateSelection(value, "Type of apartment Validator")
        }
    }

    val addressValidator = remember {
        FieldValidator(
            ValidationHelper::validateAddress
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column {
            // Fixed Header
            FixedHeaderWithBackButton(
                title = "Deep Cleaning",
                onBackClick = goBack
            )

            // Scrollable Content
            Column(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = modifier.height(22.dp))
                Text(
                    text = "Please fill in the form below with the required information",
                    style = getPoppinsRegular14(), color = Color(0xFF252525)
                )
                Spacer(modifier = modifier.height(22.dp))
                ValidatedDropdownField(
                    labelText = "Select Region",
                    placeHolder = "Select your current region",
                    fieldValidator = regionValidator,
                    defaultText = persistedFormData.region?.first ?: "",
                    options = regionData.map { it.name },
                    onSelectionChange = { selectedRegion ->
                        regionId = regionData.find { it.name == selectedRegion }?.id ?: 0
                        viewModel.findLocationByRegion(regionId)
                    },
                    isLoading = isRegionLoading,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(15.dp))
                ValidatedDropdownField(
                    labelText = "Select Location",
                    placeHolder = "Select your current location",
                    fieldValidator = locationValidator,
                    defaultText = persistedFormData.location?.first ?: "",
                    options = locationData.map { it.name },
                    onSelectionChange = { selectedLocation ->
                        locationId = locationData.find { it.name == selectedLocation }?.id ?: 0

                    },
                    isLoading = isLocationLoading,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(15.dp))
                ValidatedDropdownField(
                    labelText = "Select apartment type",
                    placeHolder = "Select your type of apartment",
                    fieldValidator = typeOfApartmentValidator,
                    defaultText = persistedFormData.typeOfApartment?.first ?: "",
                    options = apartmentTypeData.map { it.name },
                    onSelectionChange = { selectedApartment ->
                        apartmentId =
                            apartmentTypeData.find { it.name == selectedApartment }?.id ?: 0

                    },
                    isLoading = isApartmentTypeLoading,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(15.dp))
                ValidatedTextField(
                    labelText = "Address",
                    placeHolder = "Enter you address ",
                    fieldValidator = addressValidator,
                    defaultText = persistedFormData.address?.first ?: "",
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                )
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
                Spacer(modifier = Modifier.height(15.dp))

                if (imageUrl.isEmpty()) {
                    EmptyState(
                        "No Image!",
                        "Your images will be displayed here",
                        modifier = Modifier.padding(24.dp)
                    ) // TODO: add the image icon here
                } else {
                    Column(modifier = modifier.padding(24.dp)) {
                        ImageGrid(imageUrl, deleteImage = { image ->
                            viewModel.removeImageUrl(image)
                        })
                    }
                }
            }
        }

        PrimaryButton("Proceed to Checkout", {




        }, modifier = Modifier.align(BottomCenter).padding(20.dp))

        // Snackbar above the button
        CustomSnackbarHost(
            snackbarHostState = snackBarHostState,
            modifier = Modifier.align(BottomCenter).padding(bottom = 90.dp)
        )

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

    }
}