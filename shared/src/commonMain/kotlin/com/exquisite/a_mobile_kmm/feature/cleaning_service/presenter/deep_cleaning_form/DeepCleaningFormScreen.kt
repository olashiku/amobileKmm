package com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.deep_cleaning_form

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exquisite.a_mobile_kmm.core.nav.NavigationUtils
import com.exquisite.a_mobile_kmm.core.screenUtils.FieldValidator
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationHelper
import com.exquisite.a_mobile_kmm.core.screen_components.FixedHeaderWithBackButton
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.screen_components.RadioOptionGroup
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedDropdownField
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedTextField
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.ApartmentTypeModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.CleaningPriceModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.CleaningTypeModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.LocationModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.NumberOfRoomsModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.PaymentResponseModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.RegionModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.cleaningType
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.LoadingDialog
import com.exquisite.dripp.core.components.rememberSnackBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DeepCleaningFormScreen(
    goBack: () -> Unit,
    goToPrice: (String,String) -> Unit, modifier: Modifier = Modifier,
    viewModel: DeepCleaningFormViewModel = koinViewModel<DeepCleaningFormViewModel>()
) {

    val viewModelState = viewModel.deepCleaningFormState.collectAsStateWithLifecycle()
    val persistedFormData by viewModel.persistedFormData.collectAsStateWithLifecycle()

    val regionData by viewModel.regions.collectAsStateWithLifecycle()
    val locationData by viewModel.locations.collectAsStateWithLifecycle()
    val apartmentTypeData by viewModel.apartmentTypes.collectAsStateWithLifecycle()
    val cleaningTypeData by viewModel.cleaningTypes.collectAsStateWithLifecycle()
    val numberOfRoomsModelData by viewModel.numberOfRooms.collectAsStateWithLifecycle()

    val isRegionLoading by viewModel.isRegionLoading.collectAsStateWithLifecycle()
    val isLocationLoading by viewModel.isLocationLoading.collectAsStateWithLifecycle()
    val isApartmentTypeLoading by viewModel.isApartmentTypeLoading.collectAsStateWithLifecycle()
    val isNumberOfRoomsLoading by viewModel.isNumberOfRoomsLoading.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

    var cleaningPriceData by remember { mutableStateOf<CleaningPriceModel?>(null) }
    var paymentResponseData by remember { mutableStateOf<PaymentResponseModel?>(null) }
    val (snackBar, snackBarHostState) = rememberSnackBar()

    var cleaningTypeOption by remember { mutableStateOf<String?>(persistedFormData.cleaningType?.first?:"") }

    var regionId by remember { mutableStateOf(persistedFormData.region?.second?.toInt()?:0) }
    var locationId by remember { mutableStateOf(persistedFormData.location?.second?.toInt()?:0) }
    var apartmentId by remember { mutableStateOf(persistedFormData.typeOfApartment?.second?.toInt()?:0) }
    var cleaningTypeId by remember { mutableStateOf(persistedFormData.cleaningType?.second?.toInt()?:0) }
    var numberOfRoomsId by remember { mutableStateOf(persistedFormData.numberOfRooms?.second?.toInt()?:0) }

    var showPriceLoadingDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit){
        viewModel.callAllEndpoints()
    }

    // Handle error messages
    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            snackBar.showError(message)
            viewModel.clearError()
        }
    }

    when (val result = viewModelState.value) {
        is DeepCleaningFormState.Idle -> {}
        is DeepCleaningFormState.Loading -> {
            LoadingDialog(true)
        }

        is DeepCleaningFormState.PriceSuccess -> {
            cleaningPriceData = result.cleaningPriceModel
            showPriceLoadingDialog = false
            LaunchedEffect(result){
                viewModel.clearState()
                goToPrice.invoke(NavigationUtils.encodeObject(cleaningPriceData), NavigationUtils.encodeObject(persistedFormData))
            }
        }

        is DeepCleaningFormState.PaymentSuccess -> {
            paymentResponseData = result.paymentResponse
        }

        is DeepCleaningFormState.Error -> {
            showPriceLoadingDialog = false
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

    val numberOfRoomsValidator = remember {
        FieldValidator { value ->
            ValidationHelper.validateSelection(value, "Number of rooms Validator")
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
                    defaultText = persistedFormData.region?.first?:"",
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
                    defaultText = persistedFormData.location?.first?:"",
                    options = locationData.map { it.name },
                    onSelectionChange = { selectedLocation ->
                        locationId = locationData.find { it.name == selectedLocation }?.id ?: 0

                    },
                    isLoading = isLocationLoading,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(15.dp))
                ValidatedTextField(
                    labelText = "Address",
                    placeHolder = "Enter you address ",
                    fieldValidator = addressValidator,
                    defaultText = persistedFormData.address?.first?:"",
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = Modifier.height(15.dp))
                ValidatedDropdownField(
                    labelText = "Select apartment type",
                    placeHolder = "Select your type of apartment",
                    fieldValidator = typeOfApartmentValidator,
                    defaultText = persistedFormData.typeOfApartment?.first?:"",
                    options = apartmentTypeData.map { it.name },
                    onSelectionChange = { selectedApartment ->
                        apartmentId =
                            apartmentTypeData.find { it.name == selectedApartment }?.id ?: 0

                    },
                    isLoading = isApartmentTypeLoading,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Please select the appropriate button",
                    style = getPoppinsRegular14(), color = Color(0xFF252525)
                )
                Spacer(modifier = Modifier.height(9.dp))
                RadioOptionGroup(
                    options = cleaningType,
                    selectedOptionId = cleaningTypeOption,
                    onOptionSelected = { option ->
                        cleaningTypeOption = option.id
                        cleaningTypeId = option.id.toInt()
                    },
                    titleStyle = getPoppinsMedium14(),
                    subtitleStyle = getPoppinsRegular12()
                )
                Spacer(modifier = Modifier.height(15.dp))
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
            }
        }

        PrimaryButton("Continue", {
            val isRegionValid = regionValidator.forceValidation()
            val isLocationValida = locationValidator.forceValidation()
            val isTypeOfApartmentValid = typeOfApartmentValidator.forceValidation()
            val isNumberOfRoomsValid = numberOfRoomsValidator.forceValidation()
            val isAddressValid = addressValidator.forceValidation()
            if (isRegionValid && isLocationValida && isTypeOfApartmentValid && isNumberOfRoomsValid && isAddressValid && cleaningTypeOption != null) {

                viewModel.saveFormData(
                    region = regionValidator.value.value to regionId.toString(),
                    location = locationValidator.value.value to locationId.toString(),
                    typeOfApartment = typeOfApartmentValidator.value.value to apartmentId.toString(),
                    numberOfRooms = numberOfRoomsValidator.value.value to cleaningTypeId.toString(),
                    cleaningType = (cleaningTypeOption ?: "moving_in") to cleaningTypeId.toString(),
                    address = addressValidator.value.value to ""
                )
                showPriceLoadingDialog = true
                viewModel.getCleaningPrice(regionId,locationId,apartmentId,cleaningTypeId,numberOfRoomsId)

            } else {
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
