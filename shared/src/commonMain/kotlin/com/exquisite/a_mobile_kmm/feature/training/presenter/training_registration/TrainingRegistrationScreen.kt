package com.exquisite.a_mobile_kmm.feature.training.presenter.training_registration

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exquisite.a_mobile_kmm.core.nav.NavigationUtils
import com.exquisite.a_mobile_kmm.core.screenUtils.FieldValidator
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationHelper
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationHelper.validateAddress
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationHelper.validateEmail
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationHelper.validateFullName
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationHelper.validatePhoneNumber
import com.exquisite.a_mobile_kmm.core.screen_components.FixedHeaderWithBackButton
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedDropdownField
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedTextField
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.domain.model.RegisterCleanerRequest
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.presenter.cleaners_registration.CleanersRegistrationViewModel
import com.exquisite.a_mobile_kmm.feature.training.domain.model.TrainingRegistrationModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TrainingRegistrationScreen(
    goBack: () -> Unit,
    goToCheckoutPage:(String) ->Unit,
    viewModel: TrainingRegistrationViewModel = koinViewModel<TrainingRegistrationViewModel>(),
    modifier: Modifier = Modifier,
) {

    val persistedFormData by viewModel.persistedFormData.collectAsStateWithLifecycle()

    val fullNameValidator = remember {
        FieldValidator(
            ValidationHelper::validateFullName
        )
    }

    val addressValidator = remember {
        FieldValidator(
            ValidationHelper::validateAddress
        )
    }

    val emailValidator = remember {
        FieldValidator(
            ValidationHelper::validateEmail
        )
    }

    val phoneValidator = remember {
        FieldValidator(
            ValidationHelper::validatePhoneNumber
        )
    }

    val genderValidator = remember {
        FieldValidator { value ->
            ValidationHelper.validateSelection(value, "Gender")
        }
    }

    val genderList = listOf(
        "Male",
        "Female"
    )


    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column {
            // Fixed Header
            FixedHeaderWithBackButton(
                title = "Training Registration Form",
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
                    labelText = "Full Name",
                    placeHolder = "Enter you full name",
                    fieldValidator = fullNameValidator,
                    defaultText = persistedFormData.fullName,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = Modifier.height(15.dp))

                ValidatedTextField(
                    labelText = "Email",
                    placeHolder = "Enter email ",
                    fieldValidator = emailValidator,
                    defaultText = persistedFormData.email,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = Modifier.height(15.dp))
                ValidatedTextField(
                    labelText = "Mobile number",
                    placeHolder = "Enter mobile number ",
                    fieldValidator = phoneValidator,
                    defaultText = persistedFormData.phone,
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next)

                Spacer(modifier = Modifier.height(15.dp))
                ValidatedTextField(
                    labelText = "Address",
                    placeHolder = "Enter you address ",
                    fieldValidator = addressValidator,
                    defaultText = persistedFormData.address,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = Modifier.height(15.dp))
                ValidatedDropdownField(
                    labelText = "Gender",
                    placeHolder = "Select your gender",
                    fieldValidator = genderValidator,
                    defaultText = persistedFormData.gender,
                    options = genderList,
                    onSelectionChange = { selectedGender ->

                    }
                )
                Spacer(modifier = Modifier.height(15.dp))
            }
        }

        PrimaryButton("Register", {
            // Save form data BEFORE validation to persist user input
            viewModel.saveFormData(
                fullName = fullNameValidator.value.value,
                email = emailValidator.value.value,
                phone = phoneValidator.value.value,
                address = addressValidator.value.value,
                gender = genderValidator.value.value,
            )

            val isFullNameValid = fullNameValidator.forceValidation()
            val isAddressValid = addressValidator.forceValidation()
            val isEmailValid = emailValidator.forceValidation()
            val isPhoneValid = phoneValidator.forceValidation()
            val isGenderValid = genderValidator.forceValidation()

            if (isFullNameValid && isAddressValid
                && isEmailValid && isPhoneValid && isGenderValid) {

                val request = TrainingRegistrationModel(
                   fullNameValidator.value.value,
                    emailValidator.value.value, phoneValidator.value.value,
                    addressValidator.value.value, genderValidator.value.value
                )
                goToCheckoutPage(NavigationUtils.encodeObject(request))
            }
        },modifier = Modifier.align(Alignment.BottomCenter).padding(20.dp))
    }
}