package com.exquisite.a_mobile_kmm.feature.address.presenter.address_list

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.back_arrow
import amobilekmm.shared.generated.resources.no_address_icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.rememberSnackBar
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddressListScreen(
    goBack: () -> Unit,
    addNewAddress: () -> Unit,
    viewModel: AddressListViewModel = koinViewModel<AddressListViewModel>(),
    modifier: Modifier = Modifier
) {

    val (snackBar, snackBarHostState) = rememberSnackBar()


    val state by viewModel.addressListState.collectAsState()

    when (state) {
        is AddressListState.Idle -> {}

        is AddressListState.Loading -> {}

        is AddressListState.GetAddressesSuccess -> {}

        is AddressListState.DeleteAddressSuccess -> {}

        is AddressListState.Error -> {}
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


            }
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

        // Snackbar at bottom
        CustomSnackbarHost(
            snackbarHostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter).padding(20.dp)
        )
    }
}
