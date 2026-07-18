package com.exquisite.a_mobile_kmm.feature.pest_control.presenter.pest_control_residential_form

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
import com.exquisite.a_mobile_kmm.core.screen_components.FixedHeaderWithBackButton
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedDropdownField
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.ServiceModel
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.LoadingDialog
import com.exquisite.dripp.core.components.rememberSnackBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PestControlResidentialFormScreen(
    goBack: () -> Unit,goToPricing:(String,String)->Unit, modifier: Modifier = Modifier,
    viewModel: PestControlResidentialFormViewModel = koinViewModel<PestControlResidentialFormViewModel>()
) {
    val serviceListState = viewModel.pestControlServiceListState.collectAsStateWithLifecycle()
    val priceState = viewModel.pestControlResidentialFormState.collectAsStateWithLifecycle()
    val formData by viewModel.formData.collectAsStateWithLifecycle()

    val (snackBar, snackBarHostState) = rememberSnackBar()
    var serviceList by remember { mutableStateOf<List<ServiceModel>>(emptyList()) }
    var isServiceListLoading by remember { mutableStateOf(false) }
    val numberOfRoomsModelData by viewModel.numberOfRooms.collectAsStateWithLifecycle()
    val isNumberOfRoomsLoading by viewModel.isNumberOfRoomsLoading.collectAsStateWithLifecycle()


    val serviceTypeValidator = remember {
        FieldValidator { value ->
            ValidationHelper.validateSelection(value, "Type of service")
        }
    }
    val numberOfRoomsValidator = remember {
        FieldValidator { value ->
            ValidationHelper.validateSelection(value, "Number of rooms Validator")
        }
    }


        when(val result = priceState.value){
            is PestControlResidentialFormState.PriceSuccess -> {
              LaunchedEffect(result){
                  val price = result.price
                  val formData =  viewModel.formData.value
                  goToPricing(price.amount.toString(),NavigationUtils.encodeObject(formData))
                  viewModel.clearFormData()
              }

            }
            is PestControlResidentialFormState.Loading ->{
                LoadingDialog(true)
            }
            is PestControlResidentialFormState.Idle ->{
                LoadingDialog(false)
            }
            is PestControlResidentialFormState.Error ->{
                LoadingDialog(false)
                snackBar.showError(result.message)
            }
        }


    when(val result = priceState.value){
        is PestControlResidentialFormState.Error -> {
            LoadingDialog(false)
            snackBar.showError(result.message)
        }
        is PestControlResidentialFormState.Loading -> {
            LoadingDialog(true)
        }
        is PestControlResidentialFormState.PriceSuccess -> {
            LoadingDialog(false)
        }
        is PestControlResidentialFormState.Idle -> {
            LoadingDialog(false)
        }
    }

    when (val result = serviceListState.value) {
        is PestControlServiceListState.ServiceListSuccess -> {
            isServiceListLoading = false
            serviceList = result.services
        }

        is PestControlServiceListState.Error -> {
            isServiceListLoading = false
            snackBar.showError(result.message)
        }

        is PestControlServiceListState.Idle -> {
            isServiceListLoading = false
        }

        is PestControlServiceListState.Loading -> {
            isServiceListLoading = true
        }
    }
    LaunchedEffect(Unit) {
        viewModel.apply {
            getServiceList()
            findNumberOfRooms()
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
                    .padding(start = 20.dp, end = 20.dp, bottom = 80.dp)
            ) {
                Spacer(modifier = modifier.height(22.dp))
                Text(
                    text = "Please fill in the form below with the required information",
                    style = getPoppinsRegular14(), color = Color(0xFF252525)
                )
                Spacer(modifier = modifier.height(22.dp))

                ValidatedDropdownField(
                    labelText = "Select service type",
                    placeHolder = "Select your service type",
                    fieldValidator = serviceTypeValidator,
                    defaultText = formData.selectedServiceName,
                    options = serviceList.map { it.serviceName },
                    onSelectionChange = { selectedServiceName ->
                        val serviceId =
                            serviceList.find { it.serviceName == selectedServiceName }?.id ?: 0
                        viewModel.setSelectedService(selectedServiceName, serviceId)
                    },
                    isLoading = isServiceListLoading,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = modifier.height(15.dp))
                ValidatedDropdownField(
                    labelText = "Number of rooms",
                    placeHolder = "Select number of rooms",
                    fieldValidator = numberOfRoomsValidator,
                    defaultText = formData.selectedRoomName,
                    options = numberOfRoomsModelData.map { it.name },
                    onSelectionChange = { selectedRoomName ->
                        val roomId = numberOfRoomsModelData.find { it.name == selectedRoomName }?.id ?: 0
                        viewModel.setSelectedRoom(selectedRoomName, roomId)
                    },
                    isLoading = isNumberOfRoomsLoading,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        PrimaryButton("Continue", {
            val isNumberOfRoomValid = numberOfRoomsValidator.forceValidation()
            val isServiceTypeValid = serviceTypeValidator.forceValidation()

            if(isNumberOfRoomValid && isServiceTypeValid){
                viewModel.getPestControlPrice(formData.selectedServiceId, formData.selectedRoomId)
            }
        }, modifier = Modifier.align(BottomCenter).padding(20.dp))


        // Snackbar at bottom
        CustomSnackbarHost(
            snackbarHostState = snackBarHostState,
            modifier = Modifier.align(BottomCenter).padding(20.dp)
        )
    }
}

