package com.exquisite.a_mobile_kmm.feature.cleaners_registration.presenter.cleaners_registration

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.back_arrow
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
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
import org.koin.compose.viewmodel.koinViewModel
import com.exquisite.a_mobile_kmm.core.screenUtils.FieldValidator
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationHelper
import com.exquisite.a_mobile_kmm.core.screen_components.FixedHeaderWithBackButton
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedDropdownField
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedTextField
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.domain.model.RegisterCleanerRequest
import org.jetbrains.compose.resources.painterResource

@Composable
fun CleanersRegistrationScreen(
    goBack: () -> Unit,
    goToDataCapture:(String) ->Unit,
    modifier: Modifier = Modifier,
    viewModel: CleanersRegistrationViewModel = koinViewModel<CleanersRegistrationViewModel>()
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

    val employmentValidator = remember {
        FieldValidator { value ->
            ValidationHelper.validateSelection(value, "Employment Validator")
        }
    }

    val experienceLevelValidator = remember {
        FieldValidator { value ->
            ValidationHelper.validateSelection(value, "Experience Validator")
        }
    }

    val genderList = listOf(
        "Male",
        "Female"
    )

    val employmentList = listOf(
        "Employed",
        "Unemployed",
        "Self-Employed",
        "Student",
        "Retired",
        "Other"
    )
    val experienceLevelList = listOf(
        "No Experience",
        "1 - 2 Years",
        "3 - 5 Years",
        "6 - 9 Years",
        "10 - 14 Years",
        "15+ Years"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column {
            // Fixed Header
            FixedHeaderWithBackButton(
                title = "Registration Form",
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
                ValidatedDropdownField(
                    labelText = "Employment Status",
                    placeHolder = "What is your current employment status",
                    fieldValidator = employmentValidator,
                    defaultText = persistedFormData.employmentStatus,
                    options = employmentList,
                    onSelectionChange = { employmentStatus ->

                    }
                )

                Spacer(modifier = Modifier.height(15.dp))
                ValidatedDropdownField(
                    labelText = "Experience Level",
                    placeHolder = "What is your experience level",
                    fieldValidator = experienceLevelValidator,
                    defaultText = persistedFormData.experienceLevel,
                    options = experienceLevelList,
                    onSelectionChange = { employmentStatus ->

                    }
                )


                Spacer(modifier = Modifier.height(15.dp))



            }
        }

        PrimaryButton("Register", {

            val isFullNameValid = fullNameValidator.forceValidation()
            val isAddressValid = addressValidator.forceValidation()
            val isEmailValid = emailValidator.forceValidation()
            val isPhoneValid = phoneValidator.forceValidation()
            val isGenderValid = genderValidator.forceValidation()
            val isEmploymentValid = employmentValidator.forceValidation()
            val isExperienceLevelValid = experienceLevelValidator.forceValidation()

            if (isFullNameValid && isAddressValid
                && isEmailValid && isPhoneValid && isGenderValid && isEmploymentValid && isExperienceLevelValid) {

                // Save form data to ViewModel
                viewModel.saveFormData(
                    fullName = fullNameValidator.value.value,
                    email = emailValidator.value.value,
                    phone = phoneValidator.value.value,
                    address = addressValidator.value.value,
                    gender = genderValidator.value.value,
                    employmentStatus = employmentValidator.value.value,
                    experienceLevel = experienceLevelValidator.value.value
                )

                val request =  RegisterCleanerRequest(0,fullNameValidator.value.value,
                    emailValidator.value.value,phoneValidator.value.value,
                    addressValidator.value.value,"", emptyList(),genderValidator.value.value,
                    employmentValidator.value.value,experienceLevelValidator.value.value)
                goToDataCapture(NavigationUtils.encodeObject(request))
            }


        },modifier = Modifier.align(Alignment.BottomCenter).padding(20.dp))
    }
}
