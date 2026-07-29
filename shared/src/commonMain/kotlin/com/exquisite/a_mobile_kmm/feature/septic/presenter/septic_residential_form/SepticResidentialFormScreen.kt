package com.exquisite.a_mobile_kmm.feature.septic.presenter.septic_residential_form

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.exquisite.a_mobile_kmm.core.nav.NavigationUtils
import com.exquisite.a_mobile_kmm.core.screenUtils.FieldValidator
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationHelper
import com.exquisite.a_mobile_kmm.core.screen_components.FixedHeaderWithBackButton
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedDropdownField
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.feature.septic.domain.model.SepticTruckSizeModel
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.rememberSnackBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SepticResidentialFormScreen(
    goBack: () -> Unit = {},
    goToPricing:(String)->Unit,
    viewModel: SepticResidentialFormViewModel = koinViewModel<SepticResidentialFormViewModel>(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.septicResidentialFormState.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    var truckSize by remember { mutableStateOf<List<SepticTruckSizeModel>>(emptyList()) }
    val (snackBar, snackBarHostState) = rememberSnackBar()
    var selectedTruckSize by remember { mutableStateOf<SepticTruckSizeModel?>(null) }


    val truckSizeValidator = remember {
        FieldValidator { value ->
            ValidationHelper.validateSelection(value, "Truck Size")
        }
    }

    when (val result = state) {
        is SepticResidentialFormState.Idle -> {
            isLoading = false
        }

        is SepticResidentialFormState.Loading -> {
            isLoading = true
        }

        is SepticResidentialFormState.GetTruckSizeSuccess -> {
            isLoading = false
            truckSize = result.data
        }

        is SepticResidentialFormState.Error -> {
            isLoading = false
            snackBar.showError(result.message)
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

            }
        }

        PrimaryButton("Continue", {
            val isTruckSizeValid = truckSizeValidator.forceValidation()

            if (isTruckSizeValid) {
                goToPricing(NavigationUtils.encodeObject(selectedTruckSize))
            }
        }, modifier = Modifier.align(BottomCenter).padding(20.dp))


        // Snackbar at bottom
        CustomSnackbarHost(
            snackbarHostState = snackBarHostState,
            modifier = Modifier.align(BottomCenter).padding(20.dp)
        )
    }
}
