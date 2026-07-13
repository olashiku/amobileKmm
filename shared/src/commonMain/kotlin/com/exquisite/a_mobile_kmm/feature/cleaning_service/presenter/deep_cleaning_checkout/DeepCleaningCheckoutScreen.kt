package com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.deep_cleaning_checkout

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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exquisite.a_mobile_kmm.core.screenUtils.formatBalance
import com.exquisite.a_mobile_kmm.core.screenUtils.formatTime
import com.exquisite.a_mobile_kmm.core.screen_components.Badge
import com.exquisite.a_mobile_kmm.core.screen_components.FixedHeaderWithBackButton
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.screen_components.RadioOptionGroup
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium13
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold13
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.CleaningPriceModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.CleaningSummaryData
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.DeepCleaningFormData
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.DeepCleaningFormModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.getCheckoutSummaryData
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.getDeepCleaningCheckoutBalances
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.paymentOptions
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.checkout_list.Item
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.LoadingDialog
import com.exquisite.dripp.core.components.rememberSnackBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DeepCleaningCheckoutScreen(
    deepCleaningFormData: DeepCleaningFormData,
    cleaningPriceModel: CleaningPriceModel,
    deepCleaningFormModel: DeepCleaningFormModel,
    savedStateHandle: SavedStateHandle,
    viewModel: DeepCleaningCheckoutViewModel = koinViewModel<DeepCleaningCheckoutViewModel>(),
    goBack: () -> Unit,
    goToWebView: (String) -> Unit,
    goToSuccess: (String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val (snackBar, snackBarHostState) = rememberSnackBar()
    var selectedPaymentOption by remember { mutableStateOf<String?>("standard") }
    var ref by remember { mutableStateOf("") }

    val state = viewModel.deepCleaningCheckoutState.collectAsStateWithLifecycle()
    when (val result = state.value) {
        is DeepCleaningCheckoutState.Idle -> {}

        is DeepCleaningCheckoutState.CompletePaymentSuccess -> {
            LaunchedEffect(result) {
                viewModel.clearError()
                goToSuccess.invoke(
                    "Payment Successful!✅",
                    "Thank you for placing your order. A confirmation email has been send to your mailbox.",
                    "Done"
                )
            }
        }

        is DeepCleaningCheckoutState.Error -> {
            snackBar.showError(result.message)
            viewModel.clearError()
        }

        is DeepCleaningCheckoutState.InitPaymentSuccess -> {
            LaunchedEffect(result) {
                ref = result.payment.ref
                goToWebView(result.payment.paymentLink)
                viewModel.clearError()
            }
        }

        DeepCleaningCheckoutState.Loading -> {
            LoadingDialog(true)
        }

        is DeepCleaningCheckoutState.PaymentSuccess -> {
            viewModel.clearError()
        }
    }

    LaunchedEffect(Unit) {
        savedStateHandle.getStateFlow<String?>("transaction_id", null).collect { transactionId ->
            if (!transactionId.isNullOrEmpty()) {
                viewModel.completePayment(
                    ref = ref,
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
                title = "Deep Cleaning  Checkout",
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
                        Badge("DEEP CLEANING")
                        Spacer(modifier = modifier.height(8.dp))
                        Text(
                            text = "${
                                cleaningPriceModel.cleaningType.name.lowercase()
                                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                            } service", style = getPoppinsSemiBold18(), color = Color(0XFF1A1A1A))
                    }
                    Column(modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp)) {
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            getCheckoutSummaryData(
                                deepCleaningFormData,
                                deepCleaningFormModel
                            ).forEach { item ->
                                CleaningSummaryItem(item)
                            }
                        }
                    }
                    Spacer(modifier = modifier.height(20.dp))
                }

                // TODO:  radio checkout here
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
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                getDeepCleaningCheckoutBalances(
                    cleaningPriceModel,
                    deepCleaningFormModel
                ).filter { it.balance != 0.0 }.forEach {
                    Item(checkoutItemModel = it)
                }
            }
            Spacer(modifier = modifier.height(10.dp))
            HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.dp)
            Spacer(modifier = modifier.height(20.dp))
            val balance = getDeepCleaningCheckoutBalances(
                cleaningPriceModel,
                deepCleaningFormModel
            ).sumOf { it.balance }
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Total Cost", style = getPoppinsBold14(), color = Color(0xFF252525))
                Spacer(modifier = modifier.weight(1F))
                Text(
                    text = "₦${balance.formatBalance()}",
                    style = getPoppinsBold14(),
                    color = Color(0xFF252525)
                )
            }
            Spacer(modifier = modifier.height(40.dp))
            PrimaryButton("Continue", {

                if (selectedPaymentOption == "standard") {
                    viewModel.initPayment(
                        deepCleaningFormData.region?.second?.toInt() ?: 0,
                        deepCleaningFormData.location?.second?.toInt() ?: 0,
                        deepCleaningFormData.typeOfApartment?.second?.toInt() ?: 0,
                        deepCleaningFormData.cleaningType?.second?.toInt() ?: 0,
                        deepCleaningFormData.numberOfRooms?.second?.toInt() ?: 0,
                        deepCleaningFormModel.postConstruction,
                        deepCleaningFormModel.cleaningDate.fullDate,
                        deepCleaningFormModel.cleaningTime.formatTime(),
                        deepCleaningFormData.address?.first ?: "",
                        deepCleaningFormModel.images
                    )
                } else {
                    viewModel.debitFromWallet(
                        deepCleaningFormData.region?.second?.toInt() ?: 0,
                        deepCleaningFormData.location?.second?.toInt() ?: 0,
                        deepCleaningFormData.typeOfApartment?.second?.toInt() ?: 0,
                        deepCleaningFormData.cleaningType?.second?.toInt() ?: 0,
                        deepCleaningFormData.numberOfRooms?.second?.toInt() ?: 0,
                        deepCleaningFormModel.postConstruction,
                        deepCleaningFormModel.cleaningDate.fullDate,
                        deepCleaningFormModel.cleaningTime.formatTime(),
                        deepCleaningFormData.address?.first ?: "",
                        deepCleaningFormModel.images
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

@Composable
fun CleaningSummaryItem(deepCleaningFormData: CleaningSummaryData) {
    Row {
        Text(
            text = deepCleaningFormData.title, color = Color(0xFF64748B),
            style = getPoppinsSemiBold13(),
            fontSize = 13.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = deepCleaningFormData.description,
            style = getPoppinsMedium13(),
            color = Color(0xFF1A1A1A), fontSize = 13.sp
        )
    }

}