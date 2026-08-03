package com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.mobile_toilet_event_form_one

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.warning_icon
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exquisite.a_mobile_kmm.core.nav.NavigationUtils
import com.exquisite.a_mobile_kmm.core.screen_components.FixedHeaderWithBackButton
import com.exquisite.a_mobile_kmm.core.screen_components.GenericAlertModal
import com.exquisite.a_mobile_kmm.core.screen_components.ModalButton
import com.exquisite.a_mobile_kmm.core.screen_components.ModalType
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.screen_components.RadioOptionGroup
import com.exquisite.a_mobile_kmm.core.screen_components.SingleDateCalendarSelector
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular12
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.mobileToiletTypeOption
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.LoadingDialog
import com.exquisite.dripp.core.components.rememberSnackBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MobileToiletEventFormOneScreen(
    goBack: () -> Unit,
    goToNextPage: (String, String) -> Unit,
    viewModel: MobileToiletEventFormOneViewModel = koinViewModel<MobileToiletEventFormOneViewModel>(),
    modifier: Modifier = Modifier
) {

    val formData = viewModel.formData.collectAsStateWithLifecycle()
    val availabilityState = viewModel.availabilityState.collectAsStateWithLifecycle()
    val (snackBar, snackBarHostState) = rememberSnackBar()
    var selectedCleaningOption by remember { mutableStateOf(formData.value.selectedCleaningOption) }
    var selectEventDate by remember { mutableStateOf(formData.value.eventDate) }
    var showErrorModal by remember { mutableStateOf(false) }

    when (val result = availabilityState.value) {
        is ToiletAvailabilityState.Idle -> {
            LoadingDialog(false)
        }

        is ToiletAvailabilityState.Error -> {
            viewModel.resetAvailabilityState()
            snackBar.showError("Error: ${result.message}")
        }

        is ToiletAvailabilityState.Success -> {
            viewModel.resetAvailabilityState()
            if (result.data.canPurchase) {
                goToNextPage.invoke(
                    NavigationUtils.encodeObject(formData.value),
                    NavigationUtils.encodeObject(result.data)
                )
            } else {
                showErrorModal = true
                GenericAlertModal(
                    modalType = ModalType.Error(iconRes = Res.drawable.warning_icon),
                    title = "Oops!",
                    message = "No available toilet for this date selected.",
                    primaryButton = ModalButton(
                        text = "Ok",
                        backgroundColor = Color(0xFF10B981), // Green
                        action = {
                            showErrorModal = false
                            viewModel.resetAvailabilityState()
                        }
                    )
                )

            }
        }

        is ToiletAvailabilityState.Loading -> {
            LoadingDialog(true)
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
                title = "Mobile Toilet",
                onBackClick = goBack
            )

            // Scrollable Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(start = 20.dp, end = 20.dp, bottom = 80.dp)
            ) {
                Spacer(modifier = Modifier.height(22.dp))
                Text(
                    text = "Kindly select one of these below",
                    style = getPoppinsMedium16(), color = Color(0xFF252525)
                )
                Spacer(modifier = modifier.height(10.dp))
                RadioOptionGroup(
                    options = mobileToiletTypeOption,
                    selectedOptionId = selectedCleaningOption,
                    onOptionSelected = { option ->
                        selectedCleaningOption = option.id
                        viewModel.setSelectionOption(selectedCleaningOption ?: "")
                    },
                    titleStyle = getPoppinsMedium14(),
                    subtitleStyle = getPoppinsRegular12()
                )
                Spacer(modifier = modifier.height(15.dp))
                Text(
                    text = "Event Date",
                    style = getPoppinsMedium16(), color = Color(0xFF252525)
                )
                Spacer(modifier = modifier.height(10.dp))
                SingleDateCalendarSelector(
                    selectedDate = selectEventDate,
                    onDateSelected = {
                        selectEventDate = it
                        viewModel.setSelectedDate(it)
                    },
                    excludeSundays = false,
                    excludePastDates = true
                )
            }
        }

        PrimaryButton("Continue", {
            if (selectEventDate != null) {
                viewModel.checkToiletAvailability(
                    formData.value.selectedCleaningOption?.uppercase() ?: "",
                    formData.value.eventDate?.fullDate ?: ""
                )
            } else {
                snackBar.showWarning("Please select an event date")
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
}