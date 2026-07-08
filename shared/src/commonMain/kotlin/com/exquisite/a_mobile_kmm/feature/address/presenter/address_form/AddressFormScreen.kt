package com.exquisite.a_mobile_kmm.feature.address.presenter.address_form

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.back_arrow
import amobilekmm.shared.generated.resources.email_icon
import amobilekmm.shared.generated.resources.success_icon
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exquisite.a_mobile_kmm.core.screenUtils.FieldValidator
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationHelper
import com.exquisite.a_mobile_kmm.core.screen_components.GenericAlertModal
import com.exquisite.a_mobile_kmm.core.screen_components.ModalButton
import com.exquisite.a_mobile_kmm.core.screen_components.ModalType
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedTextField
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.LoadingDialog
import com.exquisite.dripp.core.components.rememberSnackBar
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddressFormScreen(
    id: Int?, address: String?, phone: String?,
    goBack: () -> Unit, viewModel: AddressFormViewModel = koinViewModel<AddressFormViewModel>(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.addressFormState.collectAsStateWithLifecycle()
    val (snackBar, snackBarHostState) = rememberSnackBar()
    var showSuccessModal by remember { mutableStateOf(false) }


    val addressValidator = remember {
        FieldValidator(
            ValidationHelper::validateAddress
        )
    }

    val phoneValidator = remember {
        FieldValidator(
            ValidationHelper::validatePhoneNumber
        )
    }

    when (val result = state) {
        is AddressFormState.Idle -> {
        }

        is AddressFormState.Loading -> {
            LoadingDialog(true)

        }

        is AddressFormState.CreateAddressSuccess -> {
            showSuccessModal = true
        }

        is AddressFormState.UpdateAddressSuccess -> {
            showSuccessModal = true
        }

        is AddressFormState.Error -> {
            snackBar.showError("Error: ${result.message}")
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
                        text = "Billing Address",
                        style = getPoppinsSemiBold18(),
                        color = Color(0xFF252525),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            Column(modifier = modifier.padding(start = 18.dp, end = 18.dp)) {
                Spacer(modifier = modifier.height(20.dp))
                Text(
                    text = "Enter your address",
                    style = getPoppinsSemiBold18(),
                    color = Color(0xFF252525)
                )
                Spacer(modifier = modifier.height(20.dp))
                ValidatedTextField(
                    labelText = "Address",
                    placeHolder = "Enter your address",
                    fieldValidator = addressValidator,
                    defaultText = address ?: "",
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    leadingIconRes = Res.drawable.email_icon
                )
                Spacer(modifier = modifier.height(20.dp))
                if (id == null) {
                    ValidatedTextField(
                        labelText = "Phone number",
                        placeHolder = "Enter your phone number",
                        fieldValidator = phoneValidator,
                        defaultText = phone ?: "",
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done,
                        leadingIconRes = Res.drawable.email_icon
                    )
                }

            }

        }
        Column(modifier = modifier.padding(20.dp).align(Alignment.BottomCenter)) {

            PrimaryButton("Continue", {
                val phoneValid = phoneValidator.forceValidation()
                val addressValid = addressValidator.forceValidation()

                if (id != null && addressValid) {
                    viewModel.updateAddress(id, addressValidator.value.value)
                } else if (id == null && addressValid && phoneValid) {
                    viewModel.createAddress(
                        addressValidator.value.value,
                        phoneValidator.value.value
                    )
                } else {
                    snackBar.showError("Please fill in all fields correctly")
                }
            })
            Spacer(modifier = modifier.height(22.dp))
        }


        CustomSnackbarHost(
            snackbarHostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter).padding(20.dp)
        )

        if (showSuccessModal) {
            GenericAlertModal(
                modalType = ModalType.Success(iconRes = Res.drawable.success_icon),
                title = "Success!",
                message = "Your address has been saved successfully",
                primaryButton = ModalButton(
                    text = "Continue",
                    backgroundColor = Color(0xFF10B981), // Green
                    action = {
                        showSuccessModal = false
                        goBack.invoke()
                    }
                )
            )
        }


    }
}
