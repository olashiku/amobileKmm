package com.exquisite.a_mobile_kmm.feature.septic.presenter.septic_commercial_form

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.exquisite.a_mobile_kmm.core.screenUtils.FieldValidator
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationHelper
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationHelper.validateCompanyName
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationHelper.validateEmail
import com.exquisite.a_mobile_kmm.core.screen_components.FixedHeaderWithBackButton
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedTextField
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SepticCommercialFormScreen(
    goBack: () -> Unit,
    goToSuccessPage: (String, String, String) -> Unit,
    viewModel: SepticCommercialFormViewModel = koinViewModel<SepticCommercialFormViewModel>(),
    modifier: Modifier = Modifier
) {

    val state by viewModel.septicCommercialFormState.collectAsState()

    when (state) {
        is SepticCommercialFormState.Idle -> {
            // Initial state
        }

        is SepticCommercialFormState.Loading -> {
            // Show loading indicator
        }

        is SepticCommercialFormState.SendEnquirySuccess -> {
            // Show enquiry sent success
        }

        is SepticCommercialFormState.Error -> {
            // Show error message
        }
    }

    val validateBusinessName = remember {
        FieldValidator(
            ValidationHelper::validateCompanyName
        )
    }

    val emailValidator = remember {
        FieldValidator(
            ValidationHelper::validateEmail
        )
    }


    val recipientNameValidator = remember {
        FieldValidator(
            ValidationHelper::validateName
        )
    }

    val recipientEmailValidator = remember {
        FieldValidator(
            ValidationHelper::validateEmail
        )
    }

    val recipientPhoneValidator = remember {
        FieldValidator(
            ValidationHelper::validatePhoneNumber
        )
    }


    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column {
            // Fixed Header
            FixedHeaderWithBackButton(
                title = "Commercial Pest Control",
                onBackClick = goBack
            )

            // Scrollable Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(start = 20.dp, end = 20.dp, bottom = 80.dp)
            ) {
                Spacer(modifier = modifier.height(22.dp))
                Text(
                    text = "Please fill in the form below with the required information",
                    style = getPoppinsRegular14(), color = Color(0xFF252525)
                )
                Spacer(modifier = modifier.height(22.dp))

                ValidatedTextField(
                    labelText = "Business Name",
                    placeHolder = "Enter business name",
                    fieldValidator = validateBusinessName,
                    defaultText = "",
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = Modifier.height(15.dp))
                Spacer(modifier = Modifier.height(15.dp))
                ValidatedTextField(
                    labelText = "Business Email",
                    placeHolder = "Enter business email ",
                    fieldValidator = emailValidator,
                    defaultText ="",
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = Modifier.height(15.dp))
                ValidatedTextField(
                    labelText = "Business Address",
                    placeHolder = "Enter company address",
                    fieldValidator = addressValidator,
                    defaultText = formData.address,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = modifier.height(15.dp))
                ValidatedTextField(
                    labelText = "Recipient Name",
                    placeHolder = "Enter Recipient Name",
                    fieldValidator = recipientNameValidator,
                    defaultText = formData.recipientName,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = modifier.height(15.dp))

                ValidatedTextField(
                    labelText = "Recipient Phone Number",
                    placeHolder = "Enter Recipient Phone Number ",
                    fieldValidator = recipientPhoneValidator,
                    defaultText = formData.recipientPhone,
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done)
                Spacer(modifier = modifier.height(50.dp))
            }
        }
    }
}
