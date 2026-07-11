package com.exquisite.a_mobile_kmm.feature.address.presenter.address_list

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.exquisite.a_mobile_kmm.core.screen_components.FixedHeaderWithBackButton
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold18
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import com.exquisite.a_mobile_kmm.feature.address.domain.model.AddressModel
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.LoadingDialog
import com.exquisite.dripp.core.components.rememberSnackBar
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddressListScreen(
    goBack: () -> Unit,
    goBackToCheckout: () -> Unit,

    addNewAddress: (Int?,String?,String?) -> Unit,
    viewModel: AddressListViewModel = koinViewModel<AddressListViewModel>(),
    modifier: Modifier = Modifier
) {

    val (snackBar, snackBarHostState) = rememberSnackBar()
    var addressList by remember { mutableStateOf<List<AddressModel>>(emptyList()) }
    var isAddressSelected by remember { mutableStateOf(false) }

    val state by viewModel.addressListState.collectAsState()

    when (val result = state) {
        is AddressListState.Idle -> {}

        is AddressListState.Loading -> {
            LoadingDialog(true)
        }

        is AddressListState.GetAddressesSuccess -> {
            addressList = result.data
        }

        is AddressListState.Error -> {
            snackBar.showError("Error: ${result.message}")

        }
    }

    LaunchedEffect(Unit) {
        viewModel.getAddresses()
    }

    Box(
        modifier = modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column {
            // Fixed Header
            FixedHeaderWithBackButton(
                title = "Address Book",
                onBackClick = goBack
            )

            // Scrollable Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = if (isAddressSelected) 100.dp else 0.dp)
            ) {
                if (addressList.isEmpty()) {
                    // empty address
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
                                addNewAddress.invoke(null, null, null)
                            })
                        Spacer(modifier = modifier.height(40.dp))
                    }
                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        addressList.forEach { address ->
                            var showDeleteModal by remember { mutableStateOf(false) }

                            AddressItem(
                                address = address,
                                onAddressSelected = { address ->
                                    viewModel.selectAddress(address.id)
                                    viewModel.saveSelectedAddress(address)
                                    isAddressSelected = true
                                },

                                editAddress = {
                                    addNewAddress(address.id, address.address, address.phone)
                                },
                                onDeleteClick = {
                                    showDeleteModal = true
                                }
                            )

                            if (showDeleteModal) {
                                ShowModal(
                                    address = address.address,
                                    yesAction = {
                                        viewModel.deleteAddress(address.id)
                                        showDeleteModal = false
                                    },
                                    noAction = {
                                        showDeleteModal = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        // Button at bottom (in Box scope)
        if (isAddressSelected) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(20.dp)
            ) {
                PrimaryButton("Continue", {
                    goBackToCheckout()
                })
                Spacer(modifier = Modifier.height(22.dp))
            }
        }

        // Snackbar at bottom (in Box scope)
        CustomSnackbarHost(
            snackbarHostState = snackBarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp)
        )
    }
}

@Composable
private fun AddressItem(
    address: AddressModel,
    onAddressSelected: (AddressModel) -> Unit,
    editAddress: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Row(modifier = modifier.padding(horizontal = 26.dp).clickable {
            onAddressSelected.invoke(address)
        }) {
            Image(
                painter = painterResource(if (address.isSelected) Res.drawable.selected_radio_icon else Res.drawable.unselected_radio_icon),
                contentDescription = "selected"
            )
            Spacer(modifier = modifier.width(10.dp))

            Column {
                Text(
                    text = address.address,
                    style = getPoppinsSemiBold16(),
                    color = Color(0xFF252525)
                )
                Spacer(modifier = modifier.height(8.dp))
                Text(text = address.phone, color = Color(0xFF2525252))
            }
        }
        Row(modifier = modifier.padding(horizontal = 20.dp)) {
            Spacer(modifier = modifier.weight(1f))
            Image(
                painter = painterResource(Res.drawable.trash_bin_icon),
                contentDescription = "delete",
                modifier = modifier.padding(10.dp).clickable {
                    onDeleteClick()
                })
            Image(
                painter = painterResource(Res.drawable.edit_icon),
                contentDescription = "edit",
                modifier = modifier.padding(10.dp).clickable { editAddress() })

        }
        HorizontalDivider(thickness = 1.dp, color = Color(0xFFD5D5D5))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowModal(address: String, yesAction: () -> Unit, noAction: () -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()


    ModalBottomSheet(
        onDismissRequest = {
            //   showLogoutBottomSheet = false
        },
        sheetState = sheetState,
        containerColor = Color(0xFFF6F6F6),
        contentColor = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icon
            Image(
                painter = painterResource(Res.drawable.logout_icon),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Title
            Text(
                text = "Delete Confirmation", style = getPoppinsBold18(), color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Description
            Text(
                text = "Are you sure you want to delete $address?",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF525252),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Yes Button
            Button(
                onClick = {
                    scope.launch {
                        sheetState.hide()
                        //   showLogoutBottomSheet = false
                        yesAction.invoke()
                    }
                },
                shape = RoundedCornerShape(25.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF09103)
                ),
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text(
                    text = "Yes, Delete",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // No Button
            Button(
                onClick = {
                    scope.launch {
                        sheetState.hide()
                        noAction.invoke()
                    }
                },
                shape = RoundedCornerShape(25.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth().height(56.dp).border(
                    width = 1.5.dp, color = Color(0xFF2D2D2D), shape = RoundedCornerShape(25.dp)
                )
            ) {
                Text(
                    text = "No, Cancel",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
