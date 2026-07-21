package com.exquisite.a_mobile_kmm.feature.pest_control.presenter.pest_control_residential_checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exquisite.a_mobile_kmm.core.screenUtils.formatBalance
import com.exquisite.a_mobile_kmm.core.screenUtils.formatTime
import com.exquisite.a_mobile_kmm.core.screenUtils.toCompactDateFormat
import com.exquisite.a_mobile_kmm.core.screenUtils.toFormattedDate
import com.exquisite.a_mobile_kmm.core.screen_components.Badge
import com.exquisite.a_mobile_kmm.core.screen_components.FixedHeaderWithBackButton
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.screen_components.RadioOptionGroup
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.CleaningSummaryData
import com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.deep_cleaning_checkout.CleaningSummaryItem
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.paymentOptions
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.PestControlResidentialFormModel
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.ResidentialPestControlFormTwoModel
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.getPricingList
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.LoadingDialog
import com.exquisite.dripp.core.components.rememberSnackBar
import kotlinx.datetime.Clock
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PestControlResidentialCheckoutScreen(
    amount: String, pestControlResidentialFormModel: PestControlResidentialFormModel,
    residentialPestControlFormTwoModel: ResidentialPestControlFormTwoModel,
    savedStateHandle: SavedStateHandle,
    viewModel: PestControlResidentialCheckoutViewModel = koinViewModel<PestControlResidentialCheckoutViewModel>(),
    goBack: () -> Unit,
    goToWebView: (String) -> Unit,
    goToSuccess: (String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val (snackBar, snackBarHostState) = rememberSnackBar()
    var selectedPaymentOption by remember { mutableStateOf<String?>("standard") }

    val state = viewModel.pestControlResidentialCheckoutState.collectAsStateWithLifecycle()

    when (val result = state.value) {
        is PestControlResidentialCheckoutState.Idle -> {}

        is PestControlResidentialCheckoutState.CompletePaymentSuccess -> {
            LaunchedEffect(result) {
                viewModel.clearError()
                goToSuccess.invoke(
                    "Payment Successful!✅",
                    "Thank you for placing your order. A confirmation email has been send to your mailbox.",
                    "Done"
                )
            }
        }

        is PestControlResidentialCheckoutState.Error -> {
            snackBar.showError(result.message)
            viewModel.clearError()
        }

        is PestControlResidentialCheckoutState.InitPaymentSuccess -> {
            LaunchedEffect(result) {
                viewModel.saveReference(result.payment.first)
                goToWebView(result.payment.second)
                viewModel.clearError()
            }
        }

        PestControlResidentialCheckoutState.Loading -> {
            LoadingDialog(true)
        }

        is PestControlResidentialCheckoutState.DebitWalletSuccess -> {
            LaunchedEffect(result) {
                viewModel.clearError()
                goToSuccess.invoke(
                    "Payment Successful!✅",
                    "Thank you for placing your order. A confirmation email has been send to your mailbox.",
                    "Done"
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        savedStateHandle.getStateFlow<String?>("transaction_id", null).collect { transactionId ->
            if (!transactionId.isNullOrEmpty()) {
                viewModel.completePayment(
                    txnRef = transactionId
                )
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Fixed Header
            FixedHeaderWithBackButton(
                title = "Residential Pest Control Checkout",
                onBackClick = goBack
            )

            // Scrollable Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp, end = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = modifier.height(20.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth().background(Color(0XFFFFFFFF)),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0XFFFFFFFF))
                ) {
                    Column(
                        modifier = modifier.fillMaxWidth().wrapContentHeight()
                            .background(Color(0XFFFFF9F0)).padding(20.dp)
                    ) {
                        Spacer(modifier = modifier.height(1.dp))
                        Badge("RESIDENTIAL PEST CONTROL")
                        Spacer(modifier = modifier.height(8.dp))
                        Text(
                            text = pestControlResidentialFormModel.selectedServiceName,
                            style = getPoppinsSemiBold18(),
                            color = Color(0XFF1A1A1A)
                        )
                    }
                    Column(modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp)) {
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            getPricingList(pestControlResidentialFormModel).forEach { item ->
                                CleaningSummaryItem(item)
                            }
                            CleaningSummaryItem(
                                CleaningSummaryData(
                                    "Address",
                                    residentialPestControlFormTwoModel.address
                                )
                            )
                            CleaningSummaryItem(
                                CleaningSummaryData(
                                    "Inspection Date",
                                    residentialPestControlFormTwoModel.inspectionDate?.fullDate?.toCompactDateFormat()?:""
                                )
                            )
                            CleaningSummaryItem(
                                CleaningSummaryData(
                                    "Service Date",
                                    residentialPestControlFormTwoModel.serviceDate?.fullDate?.toCompactDateFormat()?:""
                                )
                            )
                        }
                    }
                    Spacer(modifier = modifier.height(20.dp))
                }

                // Scroll indicator
                Column(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Scroll down",
                        tint = Color(0xFF999999),
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Scroll for payment options",
                        style = getPoppinsRegular12(),
                        color = Color(0xFF999999)
                    )
                }

                Column {
                    Spacer(modifier = modifier.height(40.dp))

                    Text(
                        text = "Payment Options",
                        style = getPoppinsSemiBold14(),
                        color = Color(0xFF252525)
                    )
                    Spacer(modifier = modifier.height(5.dp))
                    HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.dp)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Radio option group for payment selection
                    RadioOptionGroup(
                        options = paymentOptions,
                        selectedOptionId = selectedPaymentOption,
                        onOptionSelected = { option ->
                            selectedPaymentOption = option.id

                        },
                        titleStyle = getPoppinsMedium14(),
                        subtitleStyle = getPoppinsRegular12()
                    )
                }
                // Bottom padding to prevent content from being hidden behind fixed bottom section
                Spacer(modifier = Modifier.height(350.dp))
            }
        }

        Column(
            modifier = modifier
                .align(BottomCenter)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(Color(0xFFFEF9F2))
                .padding(20.dp)
        ) {
            Text(text = "Total", style = getPoppinsSemiBold16(), color = Color(0xFF252525))
            Spacer(modifier = modifier.height(5.dp))
            HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.dp)
            Spacer(modifier = modifier.height(20.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Total Cost", style = getPoppinsBold14(), color = Color(0xFF252525))
                Spacer(modifier = modifier.weight(1F))
                Text(
                    text = "₦${amount.toDoubleOrNull()?.formatBalance() ?: "0.00"}",
                    style = getPoppinsBold14(),
                    color = Color(0xFF252525)
                )
            }
            Spacer(modifier = modifier.height(40.dp))
            PrimaryButton("Continue", {
                val uniqueRef = "PEST-${Clock.System.now().toEpochMilliseconds()}"
                if (selectedPaymentOption == "standard") {
                    viewModel.initPayment(
                        uniqueRef = uniqueRef,
                        address = residentialPestControlFormTwoModel.address,
                        images = residentialPestControlFormTwoModel.images,
                        apartmentTypeId = pestControlResidentialFormModel.selectedRoomId,
                        isHotFogging = residentialPestControlFormTwoModel.wantsHotFogging,
                        serviceDate = residentialPestControlFormTwoModel.serviceDate?.fullDate
                            ?: "",
                        inspectionDate = residentialPestControlFormTwoModel.inspectionDate?.fullDate
                            ?: "",
                        serviceTime = residentialPestControlFormTwoModel.serviceTime?.formatTime()?:"",
                        inspectionTime = residentialPestControlFormTwoModel.inspectionTime?.formatTime()?:"",
                        extraNote = residentialPestControlFormTwoModel.extraNote,
                        customerOwnVehicle = residentialPestControlFormTwoModel.hasPestInVehicle,
                        numberOfVehicles = residentialPestControlFormTwoModel.numberOfVehicles.toIntOrNull()
                            ?: 0
                    )
                } else {
                    viewModel.debitFromWallet(
                        uniqueRef = uniqueRef,
                        address = residentialPestControlFormTwoModel.address,
                        images = residentialPestControlFormTwoModel.images,
                        apartmentTypeId = pestControlResidentialFormModel.selectedRoomId,
                        isHotFogging = residentialPestControlFormTwoModel.wantsHotFogging,
                        serviceDate = residentialPestControlFormTwoModel.serviceDate?.fullDate
                            ?: "",
                        inspectionDate = residentialPestControlFormTwoModel.inspectionDate?.fullDate
                            ?: "",
                        serviceTime = residentialPestControlFormTwoModel.serviceTime?.formatTime()?:"",
                        inspectionTime = residentialPestControlFormTwoModel.inspectionTime?.formatTime()?:"",
                        extraNote = residentialPestControlFormTwoModel.extraNote,
                        customerOwnVehicle = residentialPestControlFormTwoModel.hasPestInVehicle,
                        numberOfVehicles = residentialPestControlFormTwoModel.numberOfVehicles.toIntOrNull()
                            ?: 0
                    )
                }

            })
            Spacer(modifier = modifier.height(20.dp))
        }

        // Snackbar at bottom
        CustomSnackbarHost(
            snackbarHostState = snackBarHostState,
            modifier = Modifier.align(BottomCenter).padding(20.dp)
        )
    }
}
