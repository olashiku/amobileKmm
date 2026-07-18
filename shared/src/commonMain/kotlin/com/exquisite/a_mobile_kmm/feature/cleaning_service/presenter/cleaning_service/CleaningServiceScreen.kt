package com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.cleaning_service

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.back_arrow
import amobilekmm.shared.generated.resources.success_icon
import amobilekmm.shared.generated.resources.warning_icon
import androidx.compose.foundation.Image
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.screen_components.FixedHeaderWithBackButton
import com.exquisite.a_mobile_kmm.core.screen_components.GenericAlertModal
import com.exquisite.a_mobile_kmm.core.screen_components.ModalButton
import com.exquisite.a_mobile_kmm.core.screen_components.ModalType
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.screen_components.RadioOptionGroup
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.cleaningTypeOptions
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.LoadingDialog
import com.exquisite.dripp.core.components.rememberSnackBar
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CleaningServiceScreen(
    goBack: () -> Unit,
    goToCleaning: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CleaningServiceViewModel = koinViewModel<CleaningServiceViewModel>()) {
    val (snackBar, snackBarHostState) = rememberSnackBar()
    val cleaningServiceState = viewModel.cleaningServiceState.collectAsStateWithLifecycle()
    var selectedCleaningOption by remember { mutableStateOf<String?>("deep") }
    var showErrorModal by remember { mutableStateOf(false) }


    when (val state = cleaningServiceState.value) {
        is CleaningServiceState.Success -> {
            LaunchedEffect(state) {
                if (state.isEligible) {
                    viewModel.resetState()
                    goToCleaning.invoke("basic")
                } else {
                    viewModel.resetState()
                    showErrorModal = true
                }
            }
        }

        is CleaningServiceState.Error -> {
            LaunchedEffect(state) {
                snackBar.showError(state.message)
                viewModel.resetState()
            }
        }

        is CleaningServiceState.Loading -> {
            LoadingDialog(true)
        }

        is CleaningServiceState.Idle -> {
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
                title = "Cleaning Service",
                onBackClick = goBack
            )

            // Scrollable Content
            Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
                Spacer(modifier = Modifier.height(22.dp))
                Text(
                    text = "Kindly select your cleaning option",
                    style = getPoppinsRegular14(), color = Color(0xFF252525)
                )
                Spacer(modifier = Modifier.height(16.dp))
                RadioOptionGroup(
                    options = cleaningTypeOptions,
                    selectedOptionId = selectedCleaningOption,
                    onOptionSelected = { option ->
                        selectedCleaningOption = option.id
                    },
                    titleStyle = getPoppinsMedium14(),
                    subtitleStyle = getPoppinsRegular12()
                )
            }
        }

        PrimaryButton("Continue", {
            if (selectedCleaningOption == "basic") {
               viewModel.checkBasicCleaningEligibility()
            } else {
                goToCleaning.invoke(selectedCleaningOption ?: "deep")
            }
        }, modifier = Modifier.align(Alignment.BottomCenter).padding(20.dp))

        // Snackbar at bottom
        CustomSnackbarHost(
            snackbarHostState = snackBarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )


        if (showErrorModal) {
            GenericAlertModal(
                modalType = ModalType.Error(iconRes = Res.drawable.warning_icon),
                title = "Oops!",
                message = "Not eligible for Basic Cleaning. Please select deep cleaning.",
                primaryButton = ModalButton(
                    text = "Continue",
                    backgroundColor = Color(0xFF10B981), // Green
                    action = {
                        showErrorModal = false
                    }
                )
            )
        }
    }
}