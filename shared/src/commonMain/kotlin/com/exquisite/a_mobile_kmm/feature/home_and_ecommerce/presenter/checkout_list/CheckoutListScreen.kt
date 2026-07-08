package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.checkout_list

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.back_arrow
import amobilekmm.shared.generated.resources.no_address_icon
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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exquisite.a_mobile_kmm.core.nav.NavigationUtils
import com.exquisite.a_mobile_kmm.core.screenUtils.formatBalance
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.screen_components.RadioOptionGroup
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.CheckoutItemModel
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.CreateOrderModel
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.ShippingDetail
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.getCheckoutBalances
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.paymentOptions
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.LoadingDialog
import com.exquisite.dripp.core.components.rememberSnackBar
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CheckoutListScreen(
    goBack: () -> Unit,
    addNewAddress: () -> Unit,
    continueButton: (String,String) -> Unit,
    viewModel: CheckoutListViewModel = koinViewModel<CheckoutListViewModel>(),
    modifier: Modifier = Modifier
) {

    val (snackBar, snackBarHostState) = rememberSnackBar()

    val selectedAddress by viewModel.selectedAddress.collectAsStateWithLifecycle()

    var selectedPaymentOption by remember { mutableStateOf<String?>("standard") }
    val checkoutListState = viewModel.checkoutListState.collectAsStateWithLifecycle()
    val cartState = viewModel.cartState.collectAsStateWithLifecycle().value

    when (val result = checkoutListState.value) {
        is CheckoutListState.Loading -> {
            LoadingDialog(true)
        }

        is CheckoutListState.Success -> {
        val createOrderModelString =  NavigationUtils.encodeObject(result.data)
            viewModel.clearState()
         continueButton.invoke(createOrderModelString,selectedPaymentOption?:"")
        }

        is CheckoutListState.Error -> {
            snackBar.showError(result.message)
        }

        is CheckoutListState.Idle -> {}

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
                        text = "Checkout List",
                        style = getPoppinsSemiBold18(),
                        color = Color(0xFF252525),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            Column(modifier = modifier.padding(start = 18.dp, end = 18.dp)) {
                Spacer(modifier = modifier.height(20.dp))
                Text(
                    text = "Billing Address",
                    style = getPoppinsSemiBold18(),
                    color = Color(0xFF252525)
                )
                Spacer(modifier = modifier.height(20.dp))

                // Display selected address or empty state
                if (selectedAddress != null) {
                    // Show selected address
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF6F6F6), RoundedCornerShape(12.dp))
                            .padding(16.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = selectedAddress!!.address,
                                        style = getPoppinsMedium14(),
                                        color = Color(0xFF252525)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = selectedAddress!!.phone,
                                        style = getPoppinsRegular12(),
                                        color = Color(0xFF918F8B)
                                    )
                                }
                                Text(
                                    text = "Change",
                                    style = getPoppinsMedium14(),
                                    color = Color(0xFFF09103),
                                    modifier = Modifier.clickable {
                                        addNewAddress.invoke()
                                    }
                                )
                            }
                        }
                    }
                }
            }

            if (selectedAddress == null) {
                // Show empty state
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(Res.drawable.no_address_icon),
                        contentDescription = "no address icon",
                    )
                    Text(
                        text = "No Address Found",
                        style = getPoppinsRegular14(),
                        color = Color(0xFF252525)
                    )
                    Text(
                        text = "Please Add New Address",
                        style = getPoppinsRegular14(),
                        color = Color(0xFF252525)
                    )
                    Text(
                        text = "Add New Address",
                        style = getPoppinsMedium16(),
                        color = Color(0xFFF09103),
                        modifier = modifier.padding(20.dp).clickable {
                            addNewAddress.invoke()
                        })
                    Spacer(modifier = modifier.height(40.dp))
                }
            }
            Column(modifier = modifier.padding(start = 18.dp, end = 18.dp)) {
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
                        // Handle payment option selection
                    },
                    titleStyle = getPoppinsMedium14(),
                    subtitleStyle = getPoppinsRegular12()
                )

                Spacer(modifier = Modifier.height(25.dp))
                if (selectedAddress != null) {
                    NoCancelPolicyNote()
                }


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
                getCheckoutBalances(cartState,ShippingDetail()).forEach {
                    Item(checkoutItemModel = it)
                }
            }
            Spacer(modifier = modifier.height(10.dp))
            HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.dp)
            Spacer(modifier = modifier.height(20.dp))
            val balance = getCheckoutBalances(cartState,ShippingDetail()).filter{!it.title.equals("Number of Items")}.sumOf { it.balance}
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
                if (selectedAddress != null) {
                    viewModel.createOrder()
                } else {
                    snackBar.showError("You need to select an address before you proceed")
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
fun NoCancelPolicyNote(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier =
            modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(Color(0XFFFAEE6D))
    ) {
        Text(
            text = "Please note!! There is no return or cancellation " +
                    "policy on items bought via the app. ",
            textAlign = TextAlign.Center,
            style = getPoppinsRegular12(), modifier = modifier.padding(20.dp), color = Color.Black
        )
    }
}

@Composable
fun Item(checkoutItemModel: CheckoutItemModel?, modifier: Modifier = Modifier) {

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = checkoutItemModel?.title?:"",
            style = getPoppinsMedium14(),
            color = Color(0xFF918F8B)
        )
        Spacer(modifier = modifier.weight(1F))
         if(!checkoutItemModel?.title.equals("Number of Items")){
             Text(
                 text = "₦${checkoutItemModel?.balance?.formatBalance()}",
                 style = getPoppinsMedium14(),
                 color = Color(0xFF252525)
             )
         }else {
             Text(
                 text = "${checkoutItemModel?.balance?.formatBalance()}",
                 style = getPoppinsMedium14(),
                 color = Color(0xFF252525)
             )
         }
    }
}


