package com.exquisite.a_mobile_kmm.feature.address.presenter.address_list

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
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
import com.exquisite.a_mobile_kmm.core.screen_components.EmptyState
import com.exquisite.a_mobile_kmm.core.screen_components.FixedHeaderWithBackButton
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold18
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium13
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold14
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
    from:String,
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
            viewModel.clearState()
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

            // Add New Address Button
            OutlinedButton(
                onClick = { addNewAddress.invoke(null, null, null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFFF29100)
                ),
                border = BorderStroke(
                    width = 1.5.dp,
                    color = Color(0xFFF29100)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Add New Address",
                    style = getPoppinsSemiBold16(),
                    color = Color(0xFFF29100)
                )
            }

            // Scrollable Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = if (isAddressSelected) 100.dp else 0.dp)
            ) {
                if (addressList.isEmpty()) {
                    // empty address
                    EmptyState("No Address!", "Click on Add New Address to continue.")

                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        addressList.forEach { address ->
                            var showDeleteModal by remember { mutableStateOf(false) }

                            AddressItem(
                                address = address,
                                from = from,
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
    from:String,
    onAddressSelected: (AddressModel) -> Unit,
    editAddress: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isSelected = address.isSelected
    val borderColor = if (isSelected) Color(0xFFF29100) else Color(0xFFE2E8F0)
    val backgroundColor = if (isSelected) Color(0xFFFFF8ED) else Color.White

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .border(2.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable {
                onAddressSelected.invoke(address)
            }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            // Left section: Radio button and address details
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Radio button
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .border(
                            width = 2.dp,
                            color = if (isSelected) Color(0xFFF29100) else Color(0xFFCBD5E1),
                            shape = CircleShape
                        )
                        .background(
                            color = if (isSelected) Color(0xFFF29100) else Color.Transparent,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(Color.White, CircleShape)
                        )
                    }
                }

                // Address details
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Address with icon
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = Color(0xFF64748B),
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = address.address,
                            style = getPoppinsSemiBold14(),
                            color = Color(0xFF0F172A)
                        )
                    }

                    // Phone with icon
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "Phone",
                            tint = Color(0xFF64748B),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = address.phone,
                            style = getPoppinsMedium13(),
                            color = Color(0xFF64748B)
                        )
                    }
                }
            }

            if(from.equals("profile")){
                // Right section: Edit and Delete actions
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    // Edit button
                    IconButton(
                        onClick = editAddress,
                        modifier = Modifier.size(32.dp)
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color(0xFF64748B),
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Delete button
                    IconButton(
                        onClick = onDeleteClick,
                        modifier = Modifier.size(32.dp)
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color(0xFFEF4444),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

        }
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


