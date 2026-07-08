package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.deliver_option

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.back_arrow
import amobilekmm.shared.generated.resources.selected_radio_icon
import amobilekmm.shared.generated.resources.unselected_radio_icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.exquisite.a_mobile_kmm.core.screenUtils.formatBalance
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular10
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.CreateOrderModel
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.DebitFromWalletRequest
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.InitPaymentRequest
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.ShippingDetail
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.getCheckoutBalances
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.checkout_list.Item
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.LoadingDialog
import com.exquisite.dripp.core.components.rememberSnackBar
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DeliveryOptionScreen(
    createOrderModel: CreateOrderModel,
    paymentOption: String,
    savedStateHandle: SavedStateHandle,
    goBack: () -> Unit,
    goToWebView: (String) -> Unit,
    goToSuccessScreen:(String,String) -> Unit,
    viewModel: DeliverOptionViewModel = koinViewModel<DeliverOptionViewModel>(),
    modifier: Modifier = Modifier
) {
    val (snackBar, snackBarHostState) = rememberSnackBar()
    val cartState = viewModel.cartState.collectAsStateWithLifecycle().value
    var selectedDeliveryOption by remember { mutableStateOf<ShippingDetail?>(null) }

    LaunchedEffect(Unit) {
        savedStateHandle.getStateFlow<String?>("transaction_id", null).collect { transactionId ->
            if (!transactionId.isNullOrEmpty()) {
                viewModel.completePayment(
                    orderRef = createOrderModel.orderRef,
                    txnRef = transactionId
                )
            }
        }
    }

    val deliverOptionState = viewModel.deliverOptionState.collectAsStateWithLifecycle()
    when (val result = deliverOptionState.value) {
        is DeliverOptionState.Loading -> {
            LoadingDialog(true)
        }

        is DeliverOptionState.Idle -> {}

        is DeliverOptionState.InitPaymentSuccess -> {
            LaunchedEffect(result) {
                goToWebView(result.data.paymentUrl)
                viewModel.clearState()
            }
        }

        is DeliverOptionState.PaymentSuccess -> {
            LaunchedEffect(result) {
                viewModel.clearState()
                viewModel.clearCart()
                goToSuccessScreen.invoke("Payment Successful!✅","Thank you for placing your order. A confirmation email has been send to your mailbox.")
            }
        }

        is DeliverOptionState.Error -> {
            snackBar.showError(result.message)

        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column {
            Column(modifier = modifier.padding(20.dp)) {

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { goBack.invoke() },
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.back_arrow),
                            contentDescription = "Back",
                        )
                    }
                    Text(
                        text = "Delivery Options",
                        style = getPoppinsSemiBold18(),
                        color = Color(0xFF252525),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 80.dp, bottom = 380.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(createOrderModel.shippingDetails) { shippingDetail ->
                DeliveryOptionItem(
                    shippingDetail = shippingDetail,
                    isSelected = selectedDeliveryOption?.courierId == shippingDetail.courierId,
                    onSelect = {
                        selectedDeliveryOption = shippingDetail
                    }
                )
            }
        }


        Column(
            modifier = modifier
                .align(BottomCenter)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(Color(0xFFFEF9F2))
                .padding(20.dp)
        ) {
            Text(text = "Cart Total", style = getPoppinsSemiBold16(), color = Color(0xFF252525))
            Spacer(modifier = modifier.height(5.dp))
            HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.dp)
            Spacer(modifier = modifier.height(20.dp))
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                getCheckoutBalances(cartState,selectedDeliveryOption?:ShippingDetail()).forEach {
                    Item(checkoutItemModel = it)
                }
            }
            Spacer(modifier = modifier.height(10.dp))
            HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.dp)
            Spacer(modifier = modifier.height(20.dp))
            val balance = getCheckoutBalances(cartState,selectedDeliveryOption?:ShippingDetail()).filter{!it.title.equals("Number of Items")}.sumOf { it.balance}
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
                if (selectedDeliveryOption != null && paymentOption.equals("standard")) {
                    viewModel.initPayment(
                        InitPaymentRequest(
                            createOrderModel.orderRef,
                            createOrderModel.requestToken,
                            selectedDeliveryOption!!.courierId,
                            selectedDeliveryOption!!.serviceCode,
                            selectedDeliveryOption!!.total.toString()
                        )
                    )
                } else if (selectedDeliveryOption != null && paymentOption.equals("wallet")) {
                    viewModel.debitFromWallet(
                        DebitFromWalletRequest(
                            createOrderModel.orderRef,
                            createOrderModel.requestToken,
                            selectedDeliveryOption!!.courierId,
                            selectedDeliveryOption!!.serviceCode,
                            selectedDeliveryOption!!.total.toString()
                        )
                    )
                } else {
                    snackBar.showError("You need to select a delivery option before you proceed")
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
fun DeliveryOptionItem(
    shippingDetail: ShippingDetail,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedColor = if (isSelected) 0xFFFFCB81 else 0xFFFEF9F2
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSelect() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(selectedColor)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(
                    if (isSelected) Res.drawable.selected_radio_icon
                    else Res.drawable.unselected_radio_icon
                ),
                contentDescription = if (isSelected) "Selected" else "Not selected",
                modifier = Modifier.size(24.dp)
            )

            AsyncImage(
                model = shippingDetail.courierImage,
                contentDescription = shippingDetail.courierName,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF5F5F5)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = shippingDetail.courierName,
                    style = getPoppinsMedium12(),
                    color = Color(0xFF252525)
                )
                Text(
                    text = "₦${shippingDetail.total.formatBalance()}",
                    style = getPoppinsMedium12(),
                    color = Color(0xFF252525)
                )
                Text(
                    text = shippingDetail.pickupEta,
                    style = getPoppinsRegular10(),
                    color = Color(0xFF252525)
                )
                Text(
                    text = shippingDetail.deliveryEta,
                    style = getPoppinsRegular10(),
                    color = Color(0xFF252525)
                )
            }
        }
    }
}
